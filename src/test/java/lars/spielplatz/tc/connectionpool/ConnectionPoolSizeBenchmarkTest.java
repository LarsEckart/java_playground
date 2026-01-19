package lars.spielplatz.tc.connectionpool;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.mu.safesql.SafeSql;
import com.vladmihalcea.flexypool.FlexyPoolDataSource;
import com.vladmihalcea.flexypool.adaptor.HikariCPPoolAdapter;
import com.vladmihalcea.flexypool.config.FlexyPoolConfiguration;
import com.vladmihalcea.flexypool.strategy.IncrementPoolOnTimeoutConnectionAcquisitionStrategy;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Improved benchmark that demonstrates connection pool sizing with realistic parallelism.
 *
 * <p>Unlike the basic test where all threads compete for the same 2 rows, this test:
 *
 * <ul>
 *   <li>Creates 10,000 accounts
 *   <li>Each transfer picks random source/destination accounts
 *   <li>This allows actual parallel execution at the database level
 *   <li>Shows the real impact of pool size on throughput
 * </ul>
 */
@Testcontainers
@DisabledOnOs(OS.WINDOWS)
@DisplayName("Connection Pool Benchmark (Parallel Workload)")
class ConnectionPoolSizeBenchmarkTest {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ConnectionPoolSizeBenchmarkTest.class);

  private static final int ACCOUNT_COUNT = 10_000;
  private static final long INITIAL_BALANCE = 10_000L;

  @Container
  static PostgreSQLContainer<?> postgres =
      new PostgreSQLContainer<>("postgres:17-alpine")
          .withDatabaseName("testdb")
          .withUsername("test")
          .withPassword("test");

  private HikariDataSource hikariDataSource;

  @BeforeAll
  static void setUpDatabase() throws SQLException {
    LOGGER.info("Setting up database with {} accounts...", ACCOUNT_COUNT);
    long start = System.currentTimeMillis();

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

      SafeSql.of("TRUNCATE accounts").update(connection);

      // Batch insert accounts for speed (using PreparedStatement for bulk performance)
      connection.setAutoCommit(false);
      try (PreparedStatement ps =
          SafeSql.of(
                  "INSERT INTO accounts (account_number, balance) VALUES ({account}, {balance})",
                  "",
                  0L)
              .prepareStatement(connection)) {
        for (int i = 0; i < ACCOUNT_COUNT; i++) {
          ps.setString(1, "ACC-" + String.format("%06d", i));
          ps.setLong(2, INITIAL_BALANCE);
          ps.addBatch();

          if (i % 1000 == 0) {
            ps.executeBatch();
          }
        }
        ps.executeBatch();
      }
      connection.commit();
    }

    LOGGER.info("Database setup complete in {} ms", System.currentTimeMillis() - start);
  }

  @AfterEach
  void tearDown() {
    if (hikariDataSource != null && !hikariDataSource.isClosed()) {
      hikariDataSource.close();
    }
  }

  /**
   * Compares different pool sizes with a workload that allows parallelism.
   *
   * <p>Each thread transfers between random accounts, so transactions can run in parallel when they
   * don't conflict.
   */
  @Test
  @DisplayName("Compare pool sizes with parallel workload")
  void comparePoolSizesWithParallelWorkload() throws InterruptedException {
    int[] poolSizes = {1, 2, 4, 8, 16, 32, 64};
    int threadCount = 64;
    int transfersPerThread = 10;

    LOGGER.info(
        "=== Pool Size Comparison ({} threads × {} transfers) ===",
        threadCount,
        transfersPerThread);
    LOGGER.info(
        "{} | {} | {} | {}",
        padRight("Pool Size", 10),
        padRight("Duration (ms)", 15),
        padRight("Transfers/sec", 15),
        "Notes");
    LOGGER.info("-".repeat(70));

    for (int poolSize : poolSizes) {
      hikariDataSource = createHikariDataSource(poolSize);

      TransferResult result =
          executeConcurrentTransfers(hikariDataSource, threadCount, transfersPerThread);

      int totalTransfers = threadCount * transfersPerThread;
      double transfersPerSecond = totalTransfers / (result.durationMillis() / 1000.0);

      String notes = "";
      if (poolSize == 1) notes = "Single connection bottleneck";
      else if (poolSize == 2) notes = "Still constrained";
      else if (poolSize >= 32) notes = "Diminishing returns";

      LOGGER.info(
          "{} | {} | {} | {}",
          padRight(String.valueOf(poolSize), 10),
          padRight(String.valueOf(result.durationMillis()), 15),
          padRight(String.format("%.0f", transfersPerSecond), 15),
          notes);

      hikariDataSource.close();
    }
  }

  /** Uses FlexyPool to discover the optimal pool size for this workload. */
  @Test
  @DisplayName("FlexyPool discovers optimal pool size for parallel workload")
  void flexyPoolDiscovery() throws InterruptedException {
    hikariDataSource = createHikariDataSource(1);
    hikariDataSource.setConnectionTimeout(250);

    int maxOverflowPoolSize = 20;
    int connectionAcquisitionThresholdMillis = 50;

    FlexyPoolDataSource<HikariDataSource> flexyPoolDataSource =
        new FlexyPoolDataSource<>(
            new FlexyPoolConfiguration.Builder<>(
                    "FlexyPoolBenchmark", hikariDataSource, HikariCPPoolAdapter.FACTORY)
                .build(),
            new IncrementPoolOnTimeoutConnectionAcquisitionStrategy.Factory<>(
                maxOverflowPoolSize, connectionAcquisitionThresholdMillis));

    int threadCount = 64;
    int transfersPerThread = 10;

    TransferResult result =
        executeConcurrentTransfers(flexyPoolDataSource, threadCount, transfersPerThread);

    int discoveredPoolSize = hikariDataSource.getMaximumPoolSize();
    int totalTransfers = threadCount * transfersPerThread;
    double transfersPerSecond = totalTransfers / (result.durationMillis() / 1000.0);

    LOGGER.info("=== FlexyPool Discovery Results ===");
    LOGGER.info("Discovered optimal pool size: {}", discoveredPoolSize);
    LOGGER.info("Duration: {} ms", result.durationMillis());
    LOGGER.info("Throughput: {} transfers/sec", String.format("%.0f", transfersPerSecond));
    LOGGER.info("Successful transfers: {}/{}", result.successfulTransfers(), totalTransfers);

    assertThat(discoveredPoolSize).isLessThanOrEqualTo(maxOverflowPoolSize);

    flexyPoolDataSource.stop();
  }

  /** Run with optimal vs suboptimal pool sizes to show the difference. */
  @Test
  @DisplayName("Optimal vs suboptimal pool size comparison")
  void optimalVsSuboptimal() throws InterruptedException {
    int threadCount = 64;
    int transfersPerThread = 20;
    int totalTransfers = threadCount * transfersPerThread;

    LOGGER.info("=== Optimal vs Suboptimal Comparison ===");
    LOGGER.info(
        "Workload: {} threads × {} transfers = {} total",
        threadCount,
        transfersPerThread,
        totalTransfers);

    // Too few connections
    hikariDataSource = createHikariDataSource(2);
    TransferResult tooFew =
        executeConcurrentTransfers(hikariDataSource, threadCount, transfersPerThread);
    hikariDataSource.close();

    // Optimal (around 8-16 for this workload typically)
    hikariDataSource = createHikariDataSource(8);
    TransferResult optimal =
        executeConcurrentTransfers(hikariDataSource, threadCount, transfersPerThread);
    hikariDataSource.close();

    // Too many connections
    hikariDataSource = createHikariDataSource(64);
    TransferResult tooMany =
        executeConcurrentTransfers(hikariDataSource, threadCount, transfersPerThread);
    hikariDataSource.close();

    LOGGER.info("");
    LOGGER.info(
        "Pool Size 2  (too few):  {} ms ({} transfers/sec)",
        tooFew.durationMillis(),
        String.format("%.0f", totalTransfers / (tooFew.durationMillis() / 1000.0)));
    LOGGER.info(
        "Pool Size 8  (optimal):  {} ms ({} transfers/sec)",
        optimal.durationMillis(),
        String.format("%.0f", totalTransfers / (optimal.durationMillis() / 1000.0)));
    LOGGER.info(
        "Pool Size 64 (too many): {} ms ({} transfers/sec)",
        tooMany.durationMillis(),
        String.format("%.0f", totalTransfers / (tooMany.durationMillis() / 1000.0)));

    // The optimal should generally be faster than extremes
    // (though with randomness and containerized DB, results may vary)
  }

  // Helper methods

  private HikariDataSource createHikariDataSource(int maxPoolSize) {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(postgres.getJdbcUrl());
    config.setUsername(postgres.getUsername());
    config.setPassword(postgres.getPassword());
    config.setMaximumPoolSize(maxPoolSize);
    config.setAutoCommit(false);
    config.setPoolName("BenchmarkPool-" + maxPoolSize);
    return new HikariDataSource(config);
  }

  private TransferResult executeConcurrentTransfers(
      javax.sql.DataSource dataSource, int threadCount, int transfersPerThread)
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
                  ThreadLocalRandom random = ThreadLocalRandom.current();

                  for (int t = 0; t < transfersPerThread; t++) {
                    // Pick two different random accounts
                    int sourceIdx = random.nextInt(ACCOUNT_COUNT);
                    int destIdx = random.nextInt(ACCOUNT_COUNT);
                    while (destIdx == sourceIdx) {
                      destIdx = random.nextInt(ACCOUNT_COUNT);
                    }

                    String source = "ACC-" + String.format("%06d", sourceIdx);
                    String dest = "ACC-" + String.format("%06d", destIdx);

                    try {
                      transferService.transfer(source, dest, 1L);
                      successCount.incrementAndGet();
                    } catch (Exception e) {
                      failCount.incrementAndGet();
                    }
                  }
                } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
                } finally {
                  endLatch.countDown();
                }
              })
          .start();
    }

    startLatch.countDown();
    boolean completed = endLatch.await(120, TimeUnit.SECONDS);

    long durationMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos);

    if (!completed) {
      LOGGER.warn("Not all transfers completed within timeout");
    }

    return new TransferResult(successCount.get(), failCount.get(), durationMillis);
  }

  private String padRight(String s, int n) {
    return String.format("%-" + n + "s", s);
  }

  record TransferResult(int successfulTransfers, int failedTransfers, long durationMillis) {}
}
