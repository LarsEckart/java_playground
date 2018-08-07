package lars.katas;

import java.util.List;
import java.util.stream.Collectors;

public class Greeter {

    public String greet(String... names) {
        if (names == null) {
            return "Hello, my friend.";
        }
        if (isSingleName(names)) {
            if (isAllUpperCase(names[0])) {
                return String.format("HELLO %s!", names[0].toUpperCase());
            }
            return String.format("Hello, %s.", names[0]);
        }

        if (isTwoNames(names)) {
            return String.format("Hello, %s and %s.", names[0], names[1]);
        }

        List<String> allNames = List.of(names);
        List<String> commaSeparatedNames = allNames.subList(0, allNames.size() - 1);

        String collect = String.join(", ", commaSeparatedNames);


        return String.format("Hello, %s and %s.", collect, names[names.length - 1]);
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
