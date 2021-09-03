package lars.design.pubsub;

class OriginalSubscriber {
  public void update(String text) {
    System.out.println("I was notified that text " + text + " was pressed.");
    System.out.println("The capital form is " + text.toUpperCase());
  }
}
