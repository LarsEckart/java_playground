package lars.design.pubsub;

public class IdentifySubscriber implements Subscriber {
  @Override
  public void update(String text) {
    System.out.println("I was notified that text " + text + " was pressed.");
  }
}
