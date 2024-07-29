package lars.spielplatz.redis;

import io.lettuce.core.api.StatefulRedisConnection;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class RedisTest {

  // TODO: try out https://github.com/DanielYWoo/fast-object-pool
  // TODO: try out https://github.com/chrisvest/stormpot

  private final GenericObjectPool<StatefulRedisConnection<String, String>> redisPool;

  public RedisTest(GenericObjectPool<StatefulRedisConnection<String, String>> redisPool) {
    this.redisPool = redisPool;
  }

  public void update() {
    try (StatefulRedisConnection<String, String> connection = redisPool.borrowObject()) {
      connection.sync().set("key", "value");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public String get() {
    try (StatefulRedisConnection<String, String> connection = redisPool.borrowObject()) {
      return connection.sync().get("key");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
