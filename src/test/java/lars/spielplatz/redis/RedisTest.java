package lars.spielplatz.redis;

import io.lettuce.core.api.StatefulRedisConnection;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class RedisTest {

  private final GenericObjectPool<StatefulRedisConnection<String, String>> redisPool;

  public RedisTest(GenericObjectPool<StatefulRedisConnection<String, String>> redisPool) {
    this.redisPool = redisPool;
  }
}
