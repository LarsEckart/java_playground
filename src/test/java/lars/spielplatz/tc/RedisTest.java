package lars.spielplatz.tc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.slf4j.LoggerFactory.getLogger;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisCommandExecutionException;
import io.lettuce.core.TransactionResult;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
// @EnabledOnOs({OS.LINUX, OS.MAC})
public class RedisTest {

  private static final Logger log = getLogger(RedisTest.class);

  @Container
  private GenericContainer<?> redis =
      new GenericContainer<>(DockerImageName.parse("redis:6.2.7-alpine")).withExposedPorts(6379);

  private RedisCommands<String, String> syncCommands;
  private RedisCommands<String, String> otherSyncCommands;
  private RedisClient redisClient;
  private StatefulRedisConnection<String, String> connection;
  private StatefulRedisConnection<String, String> otherConnection;

  @BeforeEach
  void setUp() {
    redisClient =
        RedisClient.create("redis://" + redis.getHost() + ":" + redis.getMappedPort(6379));
    connection = redisClient.connect();
    otherConnection = redisClient.connect();
    syncCommands = connection.sync();
    otherSyncCommands = otherConnection.sync();
  }

  @AfterEach
  void tearDown() {
    connection.close();
    redisClient.shutdown();
  }

  @Test
  void incr_for_integers() {
    syncCommands.set("balance", "1");

    syncCommands.incr("balance");

    assertThat(syncCommands.get("balance")).isEqualTo("2");
  }

  @Test
  void does_not_support_incr_with_non_integers() {
    syncCommands.set("balance", "1.3");

    assertThatExceptionOfType(RedisCommandExecutionException.class)
        .isThrownBy(() -> syncCommands.incr("balance"))
        .withMessage("ERR value is not an integer or out of range");
  }

  @Test
  void incrby() {
    syncCommands.set("balance", "1");

    syncCommands.incrby("balance", 3);

    assertThat(syncCommands.get("balance")).isEqualTo("4");
  }

  @Test
  void decr() {
    syncCommands.set("balance", "4");

    syncCommands.decr("balance");

    assertThat(syncCommands.get("balance")).isEqualTo("3");
  }

  @Test
  void decrby() {
    syncCommands.set("balance", "5");

    syncCommands.decrby("balance", 3);

    assertThat(syncCommands.get("balance")).isEqualTo("2");
  }

  @Test
  void lists() {
    syncCommands.lpush("users", "a", "b", "c");

    assertThat(syncCommands.lrange("users", 0, 2)).isEqualTo(List.of("c", "b", "a"));
  }

  @Test
  void hashes() {
    syncCommands.hset("diablo", Map.of("level", "9000"));

    assertThat(syncCommands.hget("diablo", "level")).isEqualTo("9000");
  }

  @Test
  void hashes_2() {
    syncCommands.set("users:9001", "'id': 9001, 'email': 'leto@dune.gov'");
    syncCommands.hset("users:lookup:email", Map.of("leto@dune.gov", "9001"));

    String id = syncCommands.hget("users:lookup:email", "leto@dune.gov");
    String result = syncCommands.get("users:" + id);

    assertThat(result).isEqualTo("'id': 9001, 'email': 'leto@dune.gov'");
  }

  @Test
  void transactions() throws InterruptedException {
    syncCommands.set("balance", "10");

    syncCommands.watch("balance");
    var currentBalance = Integer.parseInt(syncCommands.get("balance"));
    currentBalance += 5;

    Runnable r =
        () -> {
          var currentBalance2 = Integer.parseInt(otherSyncCommands.get("balance"));
          String otherResult =
              otherSyncCommands.set("balance", String.valueOf(currentBalance2 + 1));
          log.info("other result " + otherResult);
          log.info("other thread called set");
          var currentBalance3 = Integer.parseInt(otherSyncCommands.get("balance"));
          log.info("other thread balance: " + currentBalance3);
        };
    Thread thread = new Thread(r);
    thread.start();

    thread.join();

    TransactionResult exec;
    do {
      syncCommands.multi();
      log.info("current thread updating balance to " + currentBalance);
      String balance1 = syncCommands.set("balance", String.valueOf(currentBalance));
      log.info("result from setting " + balance1);
      log.info("current thread called set within multi");
      exec = syncCommands.exec();
    } while (exec.wasDiscarded());

    String balance = syncCommands.get("balance");
    assertThat(balance).isEqualTo("15");
  }
}
