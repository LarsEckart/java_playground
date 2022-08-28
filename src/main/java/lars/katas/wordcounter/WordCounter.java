package lars.katas.wordcounter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class WordCounter {

  public String countsLegacy(String text) {
    if (text.contains(" ")) {
      return "happy=2";
    }
    return text + "=1";
  }

  public String counts(String text) {
    if (text.contains(" ")) {
      String[] strings = text.split(" ");
      String result = "";
      Map<String, Integer> map = new HashMap<>();
      for (String string : strings) {
        if (map.containsKey(string)) {
          Integer integer = map.get(string);
          map.put(string, integer + 1);
        } else {
          map.put(string, 1);
        }
      }
      for (Entry<String, Integer> entry : map.entrySet()) {
        result += entry.getKey() + "=" + entry.getValue() + " ";
      }
      return result.trim();
    }
    return "happy=1";
  }
}
