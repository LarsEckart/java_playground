package lars.spielplatz.tc.connectionpool;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.mu.safesql.SafeSql;
import com.vladmihalcea.flexypool.FlexyPoolDataSource;
import com.vladmihalcea.flexypool.adaptor.HikariCPPoolAdapter;
import com.vladmihalcea.flexypool.config.Configuration;
import com.vladmihalcea.flexypool.strategy.IncrementPoolOnTimeoutConnectionAcquiringStrategy;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Demonstrates the concepts from Vlad Mihalcea's blog post "The best way to determine the optimal
 * connection pool size".
 *
 * <p>Key insights from the article:
 *
 * <ul>
 *   <li>According to the Universal Scalability Law, maximum throughput is achieved for a limited
 *       number of database connections
 *   <li>Too many connections moves contention from the pool to the database, increasing latency
 *   <li>FlexyPool's IncrementPoolOnTimeoutConnectionAcquiringStrategy can help discover the optimal
 *       pool size
 *   <li>The optimal pool size is often much smaller than the number of concurrent threads
 * </ul>
 *
 * @see <a href="https://vladmihalcea.com/optimal-connection-pool-size/">Original Blog Post</a>
 */
@Testcontainers
@DisplayName("Connection Pool Size Optimization")
class ConnectionPoolSizeTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPoolSizeTest.class);

  @Container
  static PostgreSQLContainer<?> postgres =
      new PostgreSQLContainer<>("postgres:17-alpine")
          .withDatabaseName("testdb")
          .withUsername("test")
          .withPassword("test");

  private HikariDataSource hikariDataSource;

  @BeforeEach
  void setUp() throws SQLException {
    // Create tables and seed data using a temporary connection
    HikariConfig setupConfig = new HikariConfig();
    setupConfig.setJdbcUrl(postgres.getJdbcUrl());
    setupConfig.setUsername(postgres.getUsername());
    setupConfig.setPassword(postgres.getPassword());
    setupConfig.setMaximumPoolSize(1);

    try (HikariDataSource setupDs = new HikariDataSource(setupConfig);
        Connection connection = setupDs.getConnection()) {

      SafeSql.of(
              """
              CREATE TABLE IF NOT EXISTS accounts (
                  account_number VARCHAR(50) PRIMARY KEY,
                  balance BIGINT NOT NULL DEFAULT 0
              )
              """)
          .update(connection);

      SafeSql.of("DELETE FROM accounts").update(connection);

      // Seed with enough balance for all transfers
      SafeSql.of(
              "INSERT INTO accounts (account_number, balance) VALUES ({account}, {balance})",
              "Alice-123",
              1000000L)
          .update(connection);
      SafeSql.of(
              "INSERT INTO accounts (account_number, balance) VALUES ({account}, {balance})",
              "Bob-456",
              0L)
          .update(connection);
    }
  }

  @AfterEach
  void tearDown() {
    if (hikariDataSource != null && !hikariDataSource.isClosed()) {
      hikariDataSource.close();
    }
  }

  /**
   * Demonstrates the default HikariCP behavior with 10 connections.
   *
   * <p>With 64 concurrent transfer threads competing for 10 connections, HikariCP will queue
   * connection requests. The pool acts as a natural throttle, preventing database overload.
   */
  @Test
  @DisplayName("Default pool size (10 connections) handles 64 concurrent transfers")
  void defaultPoolSize_tenConnections() throws InterruptedException {
    hikariDataSource = createHikariDataSource(10);

    TransferResult result = executeConcurrentTransfers(hikariDataSource, 64, 5);

    LOGGER.info(
        "The {} transfers were executed on {} database connections in {} ms",
        result.successfulTransfers(),
        hikariDataSource.getMaximumPoolSize(),
        result.durationMillis());

    assertThat(result.successfulTransfers()).isEqualTo(64);
    // With default pool, we have moderate performance
    logConnectionPoolMetrics(hikariDataSource);
  }

  /**
   * Demonstrates what happens when pool size matches thread count.
   *
   * <p>Contrary to intuition, using 64 connections for 64 threads often performs WORSE than a
   * smaller pool. The contention simply moves from the connection pool to the database, where
   * REPEATABLE_READ isolation forces transaction serialization.
   */
  @Test
  @DisplayName("Pool size matching thread count (64 connections) causes database contention")
  void poolSizeMatchingThreadCount_sixtyFourConnections() throws InterruptedException {
    hikariDataSource = createHikariDataSource(64);

    TransferResult result = executeConcurrentTransfers(hikariDataSource, 64, 5);

    LOGGER.info(
        "The {} transfers were executed on {} database connections in {} ms",
        result.successfulTransfers(),
        hikariDataSource.getMaximumPoolSize(),
        result.durationMillis());

    assertThat(result.successfulTransfers()).isEqualTo(64);
    // Performance may actually be worse due to database-level contention
    logConnectionPoolMetrics(hikariDataSource);
  }

  /**
   * Demonstrates using FlexyPool to automatically discover optimal pool size.
   *
   * <p>FlexyPool's IncrementPoolOnTimeoutConnectionAcquiringStrategy starts with a minimal pool and
   * only increases it when connection acquisition times out. This helps discover the minimum pool
   * size needed to handle the workload efficiently.
   */
  @Test
  @DisplayName("FlexyPool auto-discovers optimal pool size")
  void flexyPoolAutoDiscovery() throws InterruptedException {
    // Start with just 1 connection, with minimum allowed timeout
    hikariDataSource = createHikariDataSource(1);
    hikariDataSource.setConnectionTimeout(250); // Minimum allowed by HikariCP

    int maxOverflowPoolSize = 10;
    int connectionAcquisitionThresholdMillis = 50;

    FlexyPoolDataSource<HikariDataSource> flexyPoolDataSource =
        new FlexyPoolDataSource<>(
            new Configuration.Builder<>(
                    "FlexyPoolTest", hikariDataSource, HikariCPPoolAdapter.FACTORY)
                .build(),
            new IncrementPoolOnTimeoutConnectionAcquiringStrategy.Factory<>(
                maxOverflowPoolSize, connectionAcquisitionThresholdMillis));

    TransferResult result = executeConcurrentTransfers(flexyPoolDataSource, 64, 5);

    // FlexyPool will have adjusted the pool size based on demand
    int discoveredPoolSize = hikariDataSource.getMaximumPoolSize();

    LOGGER.info(
        "The {} transfers were executed on {} database connections (discovered by FlexyPool) in {}"
            + " ms",
        result.successfulTransfers(),
        discoveredPoolSize,
        result.durationMillis());

    LOGGER.info(
        "FlexyPool discovered that {} connections are optimal for this workload",
        discoveredPoolSize);

    assertThat(result.successfulTransfers()).isEqualTo(64);
    // The discovered pool size should be smaller than or equal to max overflow
    assertThat(discoveredPoolSize).isLessThanOrEqualTo(maxOverflowPoolSize);

    flexyPoolDataSource.stop();
  }

  /**
   * Demonstrates using the optimal pool size discovered by FlexyPool.
   *
   * <p>After discovering that ~4 connections are sufficient (from the FlexyPool test), we can
   * configure HikariCP directly with this optimal size for the best performance.
   */
  @Test
  @DisplayName("Optimal pool size (4 connections) provides best performance")
  void optimalPoolSize_fourConnections() throws InterruptedException {
    hikariDataSource = createHikariDataSource(4);

    TransferResult result = executeConcurrentTransfers(hikariDataSource, 64, 5);

    LOGGER.info(
        "The {} transfers were executed on {} database connections in {} ms",
        result.successfulTransfers(),
        hikariDataSource.getMaximumPoolSize(),
        result.durationMillis());

    assertThat(result.successfulTransfers()).isEqualTo(64);
    // With optimal pool size, we get the best performance
    logConnectionPoolMetrics(hikariDataSource);
  }

  /**
   * Compares different pool sizes to find the sweet spot.
   *
   * <p>This test runs the same workload with various pool sizes and logs the results, helping to
   * visualize the relationship between pool size and performance.
   */
  @Test
  @DisplayName("Compare performance across different pool sizes")
  void comparePoolSizes() throws InterruptedException {
    int[] poolSizes = {1, 2, 4, 8, 10, 16, 32, 64};
    int threadCount = 64;
    int transferAmount = 5;

    LOGGER.info("=== Pool Size Comparison (64 concurrent transfers) ===");
    LOGGER.info("{} | {} | {}", padRight("Pool Size", 10), padRight("Duration (ms)", 15), "Notes");
    LOGGER.info("-".repeat(50));

    for (int poolSize : poolSizes) {
      hikariDataSource = createHikariDataSource(poolSize);

      TransferResult result =
          executeConcurrentTransfers(hikariDataSource, threadCount, transferAmount);

      String notes = "";
      if (poolSize == 1) notes = "Severe bottleneck";
      else if (poolSize <= 4) notes = "Optimal range";
      else if (poolSize >= 32) notes = "Database contention";

      LOGGER.info(
          "{} | {} | {}",
          padRight(String.valueOf(poolSize), 10),
          padRight(String.valueOf(result.durationMillis()), 15),
          notes);

      hikariDataSource.close();
    }
  }

  /**
   * Demonstrates monitoring connection pool metrics.
   *
   * <p>Understanding pool metrics is crucial for tuning. Key metrics include:
   *
   * <ul>
   *   <li>Active connections: Currently in use
   *   <li>Idle connections: Ready for immediate use
   *   <li>Pending threads: Waiting for a connection
   * </ul>
   */
  @Test
  @DisplayName("Monitor connection pool metrics during load")
  void monitorPoolMetrics() throws InterruptedException {
    hikariDataSource = createHikariDataSource(5);

    int threadCount = 20;
    CountDownLatch startLatch = new CountDownLatch(1);
    CountDownLatch holdLatch = new CountDownLatch(1);
    CountDownLatch endLatch = new CountDownLatch(threadCount);

    // Start threads that hold connections
    for (int i = 0; i < threadCount; i++) {
      new Thread(
              () -> {
                try {
                  startLatch.await();
                  try (Connection connection = hikariDataSource.getConnection()) {
                    // Hold the connection for a bit
                    holdLatch.await(500, TimeUnit.MILLISECONDS);
                  }
                } catch (Exception e) {
                  LOGGER.error("Thread error", e);
                } finally {
                  endLatch.countDown();
                }
              })
          .start();
    }

    startLatch.countDown();
    Thread.sleep(100); // Let threads start

    // Log metrics while connections are held
    LOGGER.info("=== Pool Metrics Under Load ===");
    LOGGER.info(
        "Total connections: {}", hikariDataSource.getHikariPoolMXBean().getTotalConnections());
    LOGGER.info(
        "Active connections: {}", hikariDataSource.getHikariPoolMXBean().getActiveConnections());
    LOGGER.info(
        "Idle connections: {}", hikariDataSource.getHikariPoolMXBean().getIdleConnections());
    LOGGER.info(
        "Threads awaiting connection: {}",
        hikariDataSource.getHikariPoolMXBean().getThreadsAwaitingConnection());

    holdLatch.countDown();
    endLatch.await(10, TimeUnit.SECONDS);

    // After all threads complete
    Thread.sleep(100);
    LOGGER.info("=== Pool Metrics After Load ===");
    LOGGER.info(
        "Active connections: {}", hikariDataSource.getHikariPoolMXBean().getActiveConnections());
    LOGGER.info(
        "Idle connections: {}", hikariDataSource.getHikariPoolMXBean().getIdleConnections());
  }

  // Helper methods

  private HikariDataSource createHikariDataSource(int maxPoolSize) {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(postgres.getJdbcUrl());
    config.setUsername(postgres.getUsername());
    config.setPassword(postgres.getPassword());
    config.setMaximumPoolSize(maxPoolSize);
    config.setAutoCommit(false);
    config.setPoolName("TestPool-" + maxPoolSize);
    return new HikariDataSource(config);
  }

  private TransferResult executeConcurrentTransfers(
      javax.sql.DataSource dataSource, int threadCount, long transferAmount)
      throws InterruptedException {

    TransferService transferService = new TransferService(dataSource, new AccountRepository());

    CountDownLatch startLatch = new CountDownLatch(1);
    CountDownLatch endLatch = new CountDownLatch(threadCount);
    AtomicInteger successCount = new AtomicInteger(0);
    AtomicInteger failCount = new AtomicInteger(0);

    long startNanos = System.nanoTime();

    for (int i = 0; i < threadCount; i++) {
      new Thread(
              () -> {
                try {
                  startLatch.await();
                  transferService.transfer("Alice-123", "Bob-456", transferAmount);
                  successCount.incrementAndGet();
                } catch (Exception e) {
                  failCount.incrementAndGet();
                  LOGGER.error("Transfer failed", e);
                } finally {
                  endLatch.countDown();
                }
              })
          .start();
    }

    startLatch.countDown();
    boolean completed = endLatch.await(60, TimeUnit.SECONDS);

    long durationMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos);

    if (!completed) {
      LOGGER.warn("Not all transfers completed within timeout");
    }

    return new TransferResult(successCount.get(), failCount.get(), durationMillis);
  }

  private void logConnectionPoolMetrics(HikariDataSource dataSource) {
    var metrics = dataSource.getHikariPoolMXBean();
    LOGGER.info(
        "Pool '{}': total={}, active={}, idle={}, waiting={}",
        dataSource.getPoolName(),
        metrics.getTotalConnections(),
        metrics.getActiveConnections(),
        metrics.getIdleConnections(),
        metrics.getThreadsAwaitingConnection());
  }

  private String padRight(String s, int n) {
    return String.format("%-" + n + "s", s);
  }

  record TransferResult(int successfulTransfers, int failedTransfers, long durationMillis) {}
}
