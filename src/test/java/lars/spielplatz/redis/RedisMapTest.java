package lars.spielplatz.redis;

import static org.assertj.core.api.Assertions.assertThat;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@DisabledOnOs(OS.WINDOWS) // no docker on github actions windows
public class RedisMapTest {

  private static final GenericContainer<?> redis =
      new GenericContainer<>(DockerImageName.parse("redis")).withReuse(true).withExposedPorts(6379);

  private static GenericObjectPool<StatefulRedisConnection<String, String>> pool;
  private static RedisClient redisClient;
  private RedisMap redisMap;

  @BeforeAll
  static void setUpBeforeAll() {
    Assumptions.assumeTrue(DockerClientFactory.instance().isDockerAvailable());
    redis.start();
    redisClient = RedisClient.create(RedisURI.create(redis.getHost(), redis.getFirstMappedPort()));
    pool =
        ConnectionPoolSupport.createGenericObjectPool(
            () -> redisClient.connect(), new GenericObjectPoolConfig());
  }

  @BeforeEach
  void setUp() {
    redisMap = new RedisMap(pool);
  }

  @AfterEach
  void tearDown() {
    redisClient.connect().sync().flushall();
  }

  @Test
  void empty_map_is_empty() {
    assertThat(redisMap.isEmpty()).isTrue();
  }

  @Test
  void map_with_one_entry_is_not_empty() {
    redisMap.put("key", "value");
    assertThat(redisMap.isEmpty()).isFalse();
  }

  @Test
  void empty_map_has_size_0() {
    assertThat(redisMap.size()).isZero();
  }

  @Test
  void map_with_one_entry_has_size_1() {
    redisMap.put("key", "value");
    assertThat(redisMap.size()).isEqualTo(1);
  }

  @Test
  void map_contains_key() {
    redisMap.put("key", "value");
    assertThat(redisMap.containsKey("key")).isTrue();
  }
}
