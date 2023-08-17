package lars;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

  private static final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(15);

  public static void main(String[] args) {
    System.out.println("Hello World!");
  }
}
