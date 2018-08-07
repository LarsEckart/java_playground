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

        List<String> allNames = List.of(names);

        List<String> shouters = allNames.stream().filter(this::isAllUpperCase).collect(Collectors.toList());
        if (shouters.isEmpty()) {
            String toGreet = toString(allNames);

            return String.format("Hello, %s.", toGreet);
        }

        List<String> normalOnes = allNames.stream().filter(n -> !n.toUpperCase().equals(n)).collect(Collectors.toList());

        String normalNames = toString(normalOnes);

        return String.format("Hello, %s. AND HELLO %s!", normalNames, shouters.get(0));
    }

    private boolean isTwoNames(List<String> names) {
        return names.size() == 2;
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

    private String toString(List<String> names) {
        if (isTwoNames(names)) {
            return String.format("%s and %s", names.get(0), names.get(1));
        } else {
            String collect = String.join(", ", names.subList(0, names.size() - 1));
            return String.format("%s, and %s", collect, names.get(names.size() - 1));
        }
    }
}
