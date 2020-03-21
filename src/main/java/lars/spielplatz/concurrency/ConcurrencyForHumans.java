package lars.spielplatz.concurrency;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/** https://www.youtube.com/watch?v=Zm3OgyQfDTU */
public class ConcurrencyForHumans {

  /** can result in lots of threads. how would we know we're done? */
  public void oldschool(Set<Path> paths) {
    Set<Path> results = new HashSet<>();
    for (Path p : paths) {
      new Thread(
              () -> {
                // if word occurs in path
                if (true) {
                  synchronized (results) {
                    results.add(p);
                  }
                }
              })
          .start();
    }
  }

  // use Tasks instead of Threads. with an executor!
  public void tasks() {
    Runnable runnable = () -> System.out.println("hello");
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    executorService.execute(runnable);

    // or better, task computes result
    Callable<Long> task =
        () -> {
          return 42L;
        };
    Future<Long> result = executorService.submit(task);
  }

  // a fixed size thread pool (sized to the available cores) is appropriate for computationally
  // expensive tasks;
  // a variable sized pool is appropriate for tasks that are short or block a lot; a ForkJoinPool is
  // appropriate
  // for computationally expensive tasks that can be decomposed into subtasks.
  public void executorServices() {
    // good for short or blocking tasks
    ExecutorService s = Executors.newCachedThreadPool();

    int cpus = Runtime.getRuntime().availableProcessors();
    int nthreads = cpus - 2;
    // good for computation intensive tasks
    ExecutorService s2 = Executors.newFixedThreadPool(nthreads);
  }
}
