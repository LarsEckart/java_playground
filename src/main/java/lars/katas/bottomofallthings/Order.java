package lars.katas.bottomofallthings;

import java.util.List;

public interface Order {

  static Order ordererFor(String order) {
    return switch (order) {
      case "randomized" -> new Random();
      case "mostly_randomized" -> new MostlyRandom();
      default -> new Default();
    };
  }

  public List<String> order(List<String> input);
}
