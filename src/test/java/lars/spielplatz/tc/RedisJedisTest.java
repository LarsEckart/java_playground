package lars.spielplatz.tc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import redis.clients.jedis.Jedis;

@Testcontainers
// @EnabledOnOs({OS.LINUX, OS.MAC})
public class RedisJedisTest {

  private static final Logger log = getLogger(RedisJedisTest.class);

  @Container
  private GenericContainer<?> redis =
      new GenericContainer<>(DockerImageName.parse("redis:6.2.7-alpine")).withExposedPorts(6379);

  private Jedis jedis;

  @BeforeEach
  void setUp() {
    String host = redis.getHost();
    Integer firstMappedPort = redis.getFirstMappedPort();
    jedis = new Jedis(host, firstMappedPort);
  }

  @AfterEach
  void tearDown() {}

  @Test
  void incr_for_integers() {
    jedis.set("balance", "1");

    jedis.incr("balance");

    assertThat(jedis.get("balance")).isEqualTo("2");
  }
}
