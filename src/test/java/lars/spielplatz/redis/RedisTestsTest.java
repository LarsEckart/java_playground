package lars.spielplatz.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;

@DisabledOnOs(OS.WINDOWS) // no docker on github actions windows
public class RedisTestsTest {

  private static final GenericContainer<?> redis =
      new GenericContainer<>("confluent/kafka").withReuse(true).withExposedPorts(6379);

  private static GenericObjectPool<StatefulRedisConnection<String, String>> pool;
  private static RedisClient redisClient;
  private RedisTest redisKeyValueStorage;

  @BeforeAll
  static void setUpBeforeAll() {
    Assumptions.assumeTrue(DockerClientFactory.instance().isDockerAvailable());
    redis.start();
    redisClient = RedisClient.create(RedisURI.create(redis.getHost(), redis.getFirstMappedPort()));
    //    pool = ConnectionPoolSupport.createGenericObjectPool(() -> redisClient.connect(),
    //        new GenericObjectPoolConfig());
  }

  @BeforeEach
  void setUp() {
    redisKeyValueStorage = new RedisTest(pool);
  }

  @Test
  void update() {}
}
