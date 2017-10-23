package ee.lars.katas;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class RomanConverter {

    private static final Map<Integer, String> mappings;

    static {
        mappings = new LinkedHashMap<>();
        mappings.put(1000, "M");
        mappings.put(900, "CM");
        mappings.put(500, "D");
        mappings.put(400, "CD");
        mappings.put(100, "C");
        mappings.put(90, "XC");
        mappings.put(50, "L");
        mappings.put(40, "XL");
        mappings.put(10, "X");
        mappings.put(9, "IX");
        mappings.put(5, "V");
        mappings.put(4, "IV");
        mappings.put(1, "I");
    }

    public String convert(int number) {
        String result = "";
        Iterator<Integer> iterator = mappings.keySet().iterator();
        while (iterator.hasNext()) {
            int mapping = iterator.next();
            while (number >= mapping) {
                result += mappings.get(mapping);
                number -= mapping;
            }
        }

        return result;
    }
}
