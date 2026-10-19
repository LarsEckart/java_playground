package experiments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class LeastRecentlyUsedCacheTest {

  @Test
  void returns_null_for_missing_entries() {
    LeastRecentlyUsedCache<String, Integer> cache = new LeastRecentlyUsedCache<>(2);

    Integer value = cache.get("missing");

    assertThat(value).isNull();
  }

  @Test
  void evicts_the_least_recently_used_entry_when_capacity_is_exceeded() {
    LeastRecentlyUsedCache<String, Integer> cache = new LeastRecentlyUsedCache<>(2);
    cache.put("a", 1);
    cache.put("b", 2);

    cache.get("a");
    cache.put("c", 3);

    assertThat(cache.get("a")).isEqualTo(1);
    assertThat(cache.get("b")).isNull();
    assertThat(cache.get("c")).isEqualTo(3);
  }

  @Test
  void updates_existing_entries_and_refreshes_their_recency() {
    LeastRecentlyUsedCache<String, Integer> cache = new LeastRecentlyUsedCache<>(2);
    cache.put("a", 1);
    cache.put("b", 2);

    cache.put("a", 10);
    cache.put("c", 3);

    assertThat(cache.get("a")).isEqualTo(10);
    assertThat(cache.get("b")).isNull();
    assertThat(cache.get("c")).isEqualTo(3);
  }

  @Test
  void rejects_non_positive_capacity() {
    assertThatThrownBy(() -> new LeastRecentlyUsedCache<>(0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("capacity must be positive");

    assertThatThrownBy(() -> new LeastRecentlyUsedCache<>(-1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("capacity must be positive");
  }
}
