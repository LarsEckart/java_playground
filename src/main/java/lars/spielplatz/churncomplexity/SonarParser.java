package lars.spielplatz.churncomplexity;

import java.util.HashMap;
import java.util.Map;

class SonarParser {

    public Map<String, Integer> complexity(String text) {
        Map<String, Integer> result = new HashMap<>();

        String[] split = text.split("\n");
        for (int i = 0; i < split.length; i += 2) {
            String s = split[i + 1].strip();
            int value = Integer.parseInt(s);
            result.put(split[i], value);
        }
        return result;
    }

}
