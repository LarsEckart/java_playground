package lars.spielplatz.tc;

import static org.assertj.core.api.Assertions.assertThat;

import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import redis.clients.jedis.Jedis;

@Testcontainers
@EnabledOnOs({OS.LINUX, OS.MAC})
public class RedisJedisTest {

  @Container
  private static RedisContainer container =
      new RedisContainer(RedisContainer.DEFAULT_IMAGE_NAME.withTag(RedisContainer.DEFAULT_TAG));

  private Jedis jedis;

  @BeforeEach
  void setUp() {
    jedis = new Jedis(container.getRedisURI());
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
