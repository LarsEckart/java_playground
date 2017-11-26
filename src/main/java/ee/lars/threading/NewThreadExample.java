package ee.lars.threading;

import java.time.LocalTime;

public class NewThreadExample {

  public static void main(String[] args) {
    Thread timeThread = new Thread(() ->
    {
      while (true) {
        System.out.println(LocalTime.now());
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          return;
        }
      }
    }, "timeThread");
    timeThread.start();
    Thread helloThread = new Thread(() ->
    {
      while (true) {
        System.out.println("Hello world");
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          return;
        }
      }
    }, "helloThread");
    helloThread.start();
    System.out.println("I'm done, outta here!");
  }
}
