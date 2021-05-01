package lars.functional;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.function.Function;

/**
 * Memoizer from Java Concurrency in Practise
 */
public class Memoizer<A, R> implements Function<A, R> {

  private final ConcurrentMap<A, Future<R>> cache = new ConcurrentHashMap<>();
  private final Function<A, R> computable;

  public Memoizer(Function<A, R> computable) {
    this.computable = computable;
  }

  @Override
  public R apply(A arg) {
    while (true) {
      Future<R> f = cache.get(arg);
      if (f == null) {
        Callable<R> eval = () -> computable.apply(arg);
        FutureTask<R> ft = new FutureTask<>(eval);
        f = cache.putIfAbsent(arg, ft);
        if (f == null) {
          f = ft;
          ft.run();
        }
      }

      try {
        return f.get();
      } catch (CancellationException e) {
        cache.remove(arg, f);
      } catch (ExecutionException | InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
