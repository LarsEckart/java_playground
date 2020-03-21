package lars.spielplatz.javaspecialists.happyhour;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class Fibonacci {

  // we lock on it, to wait, so not making it static
  private final BigInteger RESERVED = BigInteger.valueOf(-1);

  // instead of calculating same value twice:
  // putIfAbsent insert special placeholder
  // if result is null, we are first and start
  // if result is placeholder, we wait
  // "reserved caching scheme"

  public BigInteger f(int n) {
    return this.f(n, new HashMap<>());
  }

  public BigInteger f(int n, Map<Integer, BigInteger> cache) {
    BigInteger result = cache.putIfAbsent(n, RESERVED);
    // if null, we put it in there and are first
    if (result == null) {

      final int half = (n + 1) / 2;

      final ForkJoinTask<BigInteger> f0_task =
          new RecursiveTask<BigInteger>() {
            @Override
            protected BigInteger compute() {
              return f(half - 1, cache);
            }
          }.fork();

      final BigInteger f1 = f(half, cache);
      final BigInteger f0 = f0_task.join();
      // BigInteger f0 = f(half - 1);

      if (n % 2 == 1) {
        result = f0.multiply(f0).add(f1.multiply(f1));
      } else {
        result = f0.shiftLeft(1).add(f1).multiply(f1);
      }
      synchronized (RESERVED) {
        cache.put(n, result);
        RESERVED.notifyAll();
      }
      cache.put(n, result);
    } else if (RESERVED.equals(result)) {
      try {
        synchronized (RESERVED) {
          while (Objects.equals(result = cache.get(n), RESERVED)) {
            RESERVED.wait();
          }
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return result;
  }
}
