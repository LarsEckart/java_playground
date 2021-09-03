package lars.design.pubsub;

public class CapsSubscriber implements Subscriber {

  @Override
  public void update(String text) {
    System.out.println("The capital form is " + text.toUpperCase());
  }
}
