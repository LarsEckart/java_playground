package lars.spielplatz.concurrency;

import java.util.LinkedList;

public class Threading01Solution<E> {

  private final LinkedList<E> elements = new LinkedList<>();

  public boolean offer(E e) throws InterruptedException {
    synchronized (this) {
      elements.add(e);
      notifyAll();
      return true;
    }
  }

  public E take() throws InterruptedException {
    synchronized (this) {
      while (elements.isEmpty()) {
        wait();
      }
      return elements.removeFirst();
    }
  }
}
