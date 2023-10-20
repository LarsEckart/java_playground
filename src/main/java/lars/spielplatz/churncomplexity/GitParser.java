package lars.spielplatz.churncomplexity;

import java.util.Map;
import java.util.stream.Collectors;

class GitParser {

  public Map<String, Integer> churn(String text) {
    return text.lines()
        .map(String::strip)
        .filter(s -> s.contains("src/main/"))
        .map(l -> l.split(" "))
        .collect(Collectors.toMap(s -> s[1], t -> Integer.parseInt(t[0])));
  }
}
