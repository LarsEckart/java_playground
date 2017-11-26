package ee.lars.threading;

public class CounterClient {

  public static void main(String[] args) {
    Counter counter = new Counter(Integer.MAX_VALUE - 1);
    synchronized (counter) {
      if (counter.get() < Integer.MAX_VALUE) {
        System.out.println(counter.incrementAndGet());
      } else {
        System.out.println("that would overflow");
      }
    }
  }
}
