package lars;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadPools {

    public static void main(String[] args) {
        forkJoinPool();
    }

    public static void executor() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> System.out.println("hello from another thread"));
    }

    public static void executorService() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        Callable<Integer> callableTask = () -> {
            return 42;
        };

        Future<Integer> future = executor.submit(callableTask);
        // do other stuff
        try {
            if (future.isDone()) {
                System.out.println("the answer is " + future.get());
            }
        } catch (InterruptedException | ExecutionException exception) {
            exception.printStackTrace();
        }
        executor.shutdown();
    }

    public static void scheduledExecutorService() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

        Callable<Integer> callableTask = () -> {
            return 42;
        };

        Future<Integer> future = executor.schedule(callableTask, 2, TimeUnit.SECONDS);

        executor.scheduleAtFixedRate(
                () -> System.out.println("scheduled hello"), 2000, 10, TimeUnit.SECONDS);
    }

    public static void forkJoinPool() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        BigInteger result = pool.invoke(new FactorialTask(100));
        System.out.println(result);
    }

    static class FactorialTask extends RecursiveTask<BigInteger> {

        private static final int THRESHOLD = 20;
        private final int start;
        private final int n;

        public FactorialTask(int start, int n) {
            System.out.println(Thread.currentThread().getName() + ": new FactorialTask created");
            this.start = start;
            this.n = n;
        }

        public FactorialTask(int n) {
            this(1, n);
        }

        @Override
        protected BigInteger compute() {
            if ((n - start >= THRESHOLD)) {
                return ForkJoinTask.invokeAll(createSubtask())
                        .stream()
                        .map(ForkJoinTask::join)
                        .reduce(BigInteger.ONE, BigInteger::multiply);
            } else {
                return calculate(start, n);
            }
        }

        private BigInteger calculate(int start, int n) {
            return IntStream.rangeClosed(start, n)
                    .mapToObj(BigInteger::valueOf)
                    .reduce(BigInteger.ONE, BigInteger::multiply);
        }

        private Collection<FactorialTask> createSubtask() {
            List<FactorialTask> dividedTasks = new ArrayList<>();
            int mid = (start + n) / 2;
            dividedTasks.add(new FactorialTask(start, mid));
            dividedTasks.add(new FactorialTask(mid + 1, n));
            return dividedTasks;
        }
    }
}
