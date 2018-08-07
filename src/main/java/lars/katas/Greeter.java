package lars.katas;

import java.util.List;
import java.util.stream.Collectors;

public class Greeter {

    private static final String TEMPLATE_FOR_SINGLE_NAME = "Hello, %s.";
    private static final String TEMPLATE_FOR_MULTIPLE_NAMES = "Hello, %s and %s.";

    String greet(String... names) {
        if (names == null) {
            return "Hello, my friend.";
        }
        if (isSingleName(names)) {
            if (isAllUpperCase(names[0])) {
                return String.format("HELLO %s!", names[0].toUpperCase());
            }
            return String.format(TEMPLATE_FOR_SINGLE_NAME, names[0]);
        }

        if (isTwoNames(names)) {
            return String.format(TEMPLATE_FOR_MULTIPLE_NAMES, names[0], names[1]);
        }

        List<String> commaSeparatedNames = List.of(names).subList(0, List.of(names).size() - 1);

        String collect = String.join(", ", commaSeparatedNames);

        return String.format(TEMPLATE_FOR_MULTIPLE_NAMES, collect, names[names.length - 1]);
    }


    private boolean isTwoNames(String[] names) {
        return names.length == 2;
    }

    private boolean isSingleName(String[] name) {
        return name.length == 1;
    }

    private boolean isAllUpperCase(String name) {
        return name.toUpperCase().equals(name);
    }
}
