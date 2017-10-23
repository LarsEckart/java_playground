package ee.lars.spielplatz.concurrency.tij;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by larse on 22.11.2015.
 */
public class RandomSleeper {

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new RandomSleeperTask());
        }
        exec.shutdown();
    }

    private static class RandomSleeperTask implements Runnable {

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            Random rnd = new Random();
            try {
                TimeUnit.MILLISECONDS.sleep(rnd.nextInt(100) * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            System.out.println("slept for " + (end - start) + " ms");
        }
    }
}
