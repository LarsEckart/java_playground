package de.larseckart.spielplatz.javaspecialists.happyhour;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ManagedBlocker {

    // we lock on it, to wait, so not making it static
    private final BigInteger RESERVED = BigInteger.valueOf(-1);

    public static long fibo2(int n) {
        long n0 = 0, n1 = 1;
        for (int i = 0; i < n; i++) {
            long tmp = n1;
            n1 = n1 + n0;
            n0 = tmp;
        }
        return n0;
    }

    public BigInteger f(int n) {
        Map<Integer, BigInteger> cache = new ConcurrentHashMap<>();
        cache.put(0, BigInteger.ZERO);
        cache.put(1, BigInteger.ONE);
        return f(n, cache);
    }

    // instead of calculating same value twice:õ-
    // putIfAbsent insert special placeholder
    // if resutl is null, we are first and start
    // if resutl is palceholfer, we wait
    // reserved caching scheme

    public BigInteger f(int n, Map<Integer, BigInteger> cache) {
        BigInteger result = cache.putIfAbsent(n, RESERVED);
        // if null, we put it in there and are first
        if (result == null) {

            int half = (n + 1) / 2;

            ForkJoinTask<BigInteger> f0_task = new RecursiveTask<BigInteger>() {
                @Override
                protected BigInteger compute() {
                    return f(half - 1, cache);
                }
            }.fork();

            BigInteger f1 = f(half, cache);
            BigInteger f0 = f0_task.join();
            //BigInteger f0 = f(half - 1);

            if (n % 2 == 1) {
                result = f0.multiply(f0).add(f1.multiply(f1));
            } else {
                result = f0.shiftLeft(1).add(f1).multiply(f1);
            }
            synchronized (RESERVED) {
                cache.put(n, result);
                RESERVED.notifyAll();
            }
            if (cache.putIfAbsent(n, result) != null)){

                System.out.println("we wasted " + time);
            }
            cache.put(n, result);
        } else if (result == RESERVED) {
            try {
                synchronized (RESERVED) {
                    while ((result = cache.get(n)) == RESERVED) {
                        RESERVED.wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public long fibo(int n) {
        if (n <= 1) {
            return n;
        }
        return fibo(n - 1) + fibo(n - 2);
    }
}
