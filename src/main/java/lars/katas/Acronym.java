package lars.katas;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Acronym {

  private final String phrase;

  public Acronym(String phrase) {
    this.phrase = phrase;
  }

  public String get() {
    String r = phrase.replace("-", " ");
    String[] split = r.split(" ");
    return Arrays.stream(split)
        .map(s -> s.substring(0, 1))
        .map(String::toUpperCase)
        .collect(Collectors.joining());
  }
}
