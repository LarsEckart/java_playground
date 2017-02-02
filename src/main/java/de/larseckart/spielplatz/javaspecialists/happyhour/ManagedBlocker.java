package de.larseckart.spielplatz.javaspecialists.happyhour;

import java.math.BigInteger;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ManagedBlocker {

    public static long fibo2(int n) {
        long n0 = 0, n1 = 1;
        for (int i = 0; i < n; i++) {
            long tmp = n1;
            n1 = n1 + n0;
            n0 = tmp;
        }
        return n0;
    }

    public long fibo(int n) {
        if (n <= 1) {
            return n;
        }
        return fibo(n - 1) + fibo(n - 2);
    }

    public BigInteger f(int n) {
        if (n == 0) {
            return BigInteger.ZERO;
        }
        if (n == 1) {
            return BigInteger.ONE;
        }

        int half = (n + 1) / 2;

        ForkJoinTask<BigInteger> f0_task = new RecursiveTask<BigInteger>() {
            @Override
            protected BigInteger compute() {
                return f(half - 1);
            }
        }.fork();

        BigInteger f1 = f(half);
        BigInteger f0 = f0_task.join();
        //BigInteger f0 = f(half - 1);

        if (n % 2 == 1) {
            return f0.multiply(f0).add(f1.multiply(f1));
        } else {
            return f0.shiftLeft(1).add(f1).multiply(f1);
        }
    }
}
