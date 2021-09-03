package lars.design.pubsub;

import java.io.IOException;

class Program {
  public static void main(String arg[]) throws IOException {
    System.out.println("Enter text; x to exit");
    Publisher p = new Publisher();
    Subscriber s = new IdentifySubscriber();
    p.attach(s);
    Subscriber s2 = new CapsSubscriber();
    p.attach(s2);
    boolean shouldContinue = true;
    while (shouldContinue) {
      shouldContinue = p.listenForKeyboardInput();
    }
    p.detach(s);
  }
}
