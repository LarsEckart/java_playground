package lars.spielplatz.concurrency.guards;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

  private BlockingQueue<String> blockingQueue;

  public Producer(BlockingQueue<String> drop) {
    this.blockingQueue = drop;
  }

  @Override
  public void run() {
    String importantInfo[] = {
      "Mares eat oats", "Does eat oats", "Little lambs eat ivy", "A kid will eat ivy too"
    };
    Random random = new Random();
    try {
      for (int i = 0; i < importantInfo.length; i++) {
        blockingQueue.put(importantInfo[i]);
        Thread.sleep(random.nextInt(5000));
      }
      blockingQueue.put("DONE");
    } catch (InterruptedException e) {
      System.out.println("should never happen");
    }
  }
}
