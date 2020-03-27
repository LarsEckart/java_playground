package lars.katas;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Acronym {

  private final String phrase;

  public Acronym(String phrase) {
    this.phrase = phrase;
  }

  public String get() {
    String r = phrase.replace("-", " ");
    String rr = r.replace("_", "");
    String[] split = rr.split(" ");
    return Arrays.stream(split)
        .filter(s -> !s.isEmpty())
        .map(s -> s.substring(0, 1))
        .filter(s -> Character.isLetter(s.charAt(0)))
        .map(String::toUpperCase)
        .collect(Collectors.joining());
  }
}
