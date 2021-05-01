package lars.functional;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

public class NaiveMemoizer<T, S> {

  private final ConcurrentMap<T, S> cache = new ConcurrentHashMap<>();

  public static <T, S> Function<T, S> memoize(final Function<T, S> function) {
    return new NaiveMemoizer<T, S>().doMemoize(function);
  }

  private Function<T, S> doMemoize(final Function<T, S> function) {
    return input -> cache.computeIfAbsent(input, function);
  }
}
