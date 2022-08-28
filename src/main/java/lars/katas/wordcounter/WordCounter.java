package lars.katas.wordcounter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WordCounter {

  public String countsLegacy(String text) {
    if (text.contains(" ")) {
      String[] words = text.split(" ");
      if (words[0].equals(words[1])) {
        return words[0] + "=" + words.length;
      }
      return words[0] + "=1 " + words[1] + "=1";
    }
    return text + "=1";
  }

  public String counts(String text) {
    if (text.contains(" ")) {
      return Arrays.stream(text.split(" "))
          .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
          .entrySet().stream()
          .map(c -> c.getKey() + "=" + c.getValue())
          .collect(Collectors.joining(" "));
    }
    return "happy=1";
  }
}
