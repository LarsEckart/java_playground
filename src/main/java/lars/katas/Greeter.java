package lars.katas;

public class Greeter {

    public String greet(String... name) {
        if (name == null) {
            return "Hello, my friend.";
        }
        if (isSingleName(name)) {
            if (isAllUpperCase(name[0])) {
                return String.format("HELLO %s!", name[0].toUpperCase());
            }
            return String.format("Hello, %s.", name[0]);
        }

        return String.format("Hello, %s and %s.", name[0], name[1]);
    }

    private boolean isSingleName(String[] name) {
        return name.length == 1;
    }

    private boolean isAllUpperCase(String name) {
        return name.toUpperCase().equals(name);
    }
}
