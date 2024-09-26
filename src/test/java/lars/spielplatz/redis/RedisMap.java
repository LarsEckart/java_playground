package lars.spielplatz.redis;

import io.lettuce.core.api.StatefulRedisConnection;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.jetbrains.annotations.Nullable;

public class RedisMap implements Map<String, String> {

  // TODO: try out https://github.com/DanielYWoo/fast-object-pool
  // TODO: try out https://github.com/chrisvest/stormpot

  private final GenericObjectPool<StatefulRedisConnection<String, String>> redisPool;

  public RedisMap(GenericObjectPool<StatefulRedisConnection<String, String>> redisPool) {
    this.redisPool = redisPool;
  }

  @Override
  public int size() {
    try (StatefulRedisConnection<String, String> connection = redisPool.borrowObject()) {
      return connection.sync().keys("*").size();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public boolean containsKey(Object key) {
    try (StatefulRedisConnection<String, String> connection = redisPool.borrowObject()) {
      return connection.sync().exists((String) key) == 1;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean containsValue(Object value) {
    try (StatefulRedisConnection<String, String> connection = redisPool.borrowObject()) {
      List<String> keys = connection.sync().keys("*");
      for (String key : keys) {
        if (connection.sync().get(key).equals(value)) {
          return true;
        }
      }
      return false;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String get(Object key) {
    try (StatefulRedisConnection<String, String> connection = redisPool.borrowObject()) {
      return connection.sync().get((String) key);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public @Nullable String put(String key, String value) {
    try (StatefulRedisConnection<String, String> connection = redisPool.borrowObject()) {
      return connection.sync().set((String) key, value);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String remove(Object key) {
    try (StatefulRedisConnection<String, String> connection = redisPool.borrowObject()) {
      String value = connection.sync().get((String) key);
      connection.sync().del((String) key);
      return value;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void putAll(Map<? extends String, ? extends String> map) {
    map.forEach(this::put);
  }

  @Override
  public void clear() {
    try (StatefulRedisConnection<String, String> connection = redisPool.borrowObject()) {
      connection.sync().flushall();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Set<String> keySet() {
    try (StatefulRedisConnection<String, String> connection = redisPool.borrowObject()) {
      return Set.copyOf(connection.sync().keys("*"));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Collection<String> values() {
    return List.of();
  }

  @Override
  public Set<Entry<String, String>> entrySet() {
    try (StatefulRedisConnection<String, String> connection = redisPool.borrowObject()) {
      List<String> keys = connection.sync().keys("*");
      return keys.stream()
          .map(key -> Map.entry(key, connection.sync().get(key)))
          .collect(Collectors.toSet());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
