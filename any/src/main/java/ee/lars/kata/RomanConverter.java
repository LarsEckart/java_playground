package ee.lars.kata;

import java.util.LinkedHashMap;
import java.util.Map;

public class RomanConverter {

    private static final Map<Integer, String> results;

    static {
        results = new LinkedHashMap<>();
        results.put(1, "I");
        results.put(4, "IV");
        results.put(5, "V");
    }

    public String convert(int number) {
        if (results.containsKey(number)) {
            return results.get(number);
        }
        if (number > 5) {
            return results.get(5) + convert(number - 5);
        }
        return results.get(1) + convert(number - 1);
    }
}
