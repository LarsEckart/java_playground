package lars.spielplatz.javaspecialists.issue257;

import static java.util.concurrent.Executors.newFixedThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Phaser;

public class LockStepPhaser extends LockStepExample {

  public static void main(String... args) {
    LockStepPhaser lse = new LockStepPhaser();
    ExecutorService pool = newFixedThreadPool(TASKS_PER_BATCH);
    Phaser phaser = new Phaser(TASKS_PER_BATCH);
    for (int batch = 0; batch < BATCHES; batch++) {
      for (int i = 0; i < TASKS_PER_BATCH; i++) {
        pool.submit(() -> lse.task(phaser));
      }
    }
    pool.shutdown();
  }

  public void task(Phaser phaser) {
    phaser.arriveAndAwaitAdvance();
    doTask(phaser.getPhase());
  }
}
