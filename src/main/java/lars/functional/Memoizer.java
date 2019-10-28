package lars.functional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class Memoizer<T, S> {

    private final Map<T, S> memoizanCache = new ConcurrentHashMap<>();

    public static <T, S> Function<T, S> memoize(final Function<T, S> function) {
        return new Memoizer<T, S>().doMemoize(function);
    }

    private Function<T, S> doMemoize(final Function<T, S> function) {
        return input -> memoizanCache.computeIfAbsent(input, function);
    }
}
