package lars.katas;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Acronym {

  private final String phrase;

  public Acronym(String phrase) {
    this.phrase = phrase;
  }

  public String get() {
    return Stream.of(phrase)
        .map(p -> p.replace("-", " "))
        .map(s -> s.replace("_", ""))
        .flatMap(s -> Arrays.stream(s.split("\\p{Zs}+")))
        .map(s -> s.substring(0, 1))
        .map(String::toUpperCase)
        .collect(Collectors.joining());
  }
}
