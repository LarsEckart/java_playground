package lars.design.pubsub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Publisher {
  private List<Subscriber> subscribers = new ArrayList<>();

  public void attach(Subscriber s) {
    subscribers.add(s);
  }

  public void detach(Subscriber s) {
    subscribers.remove(s);
  }

  public void notify(String text) {
    for (Subscriber s : subscribers) {
      s.update(text);
    }
  }

  public boolean listenForKeyboardInput() throws IOException {
    String text = new BufferedReader(new InputStreamReader(System.in)).readLine();
    System.out.println("The text " + text + " was entered.");
    notify(text);
    return !text.equals("x");
  }
}
