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
        results.put(9, "IX");
        results.put(10, "X");
        results.put(40, "XL");
    }

    public String convert(int number) {
        if (results.containsKey(number)) {
            return results.get(number);
        }
        String result = "";
        while(number > 40) {
            result += "XL";
            number -= 40;
        }
        while(number > 10) {
            result += "X";
            number -= 10;
        }
        while(number > 5) {
            result += "V";
            number -= 5;
        }
        while(number >= 4) {
            result += "IV";
            number -= 4;
        }
        while(number >= 1) {
            result += "I";
            number -= 1;
        }
        return result;
    }
}
