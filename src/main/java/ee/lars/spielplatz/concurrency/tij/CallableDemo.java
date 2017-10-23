package ee.lars.spielplatz.concurrency.tij;

import java.util.ArrayList;
import java.util.concurrent.*;

public class CallableDemo {

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        ArrayList<Future<String>> results = new ArrayList<Future<String>>();

        for (int i = 0; i < 10; i++) {
            results.add(exec.submit(new TaskWithResult(i)));
        }

        for (Future<String> fs : results) {
            try {
                System.out.println(fs.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            } catch (ExecutionException e) {
                e.printStackTrace();
                return;
            } finally {

            }
        }

        exec.shutdown();
    }


    private static class TaskWithResult implements Callable<String> {
        private int id;

        public TaskWithResult(int id) {
            this.id = id;
        }

        @Override
        public String call() {
            return "result of TaskWithResult " + id;
        }
    }
}
