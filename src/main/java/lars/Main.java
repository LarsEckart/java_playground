package lars;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

  private static final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(15);

  public static void main(String[] args) {
    Thread t =
        new Thread(
            () -> {
              while (true) {
                try {
                  queue.take().run();
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
              }
            });
    t.start();

    for (int i = 0; i < 10; i++) {
      final int j = i;
      queue.add(
          () -> {
            System.out.println("Hello" + j);
          });
    }
  }
}
