package lars.katas;

public class Greeter {

    public String greet(String... name) {
        if (name == null) {
            return "Hello, my friend.";
        }
        if (isAllUpperCase(name[0])) {
            return String.format("HELLO %s!", name[0].toUpperCase());
        }
        return String.format("Hello, %s.", name);
    }

    private boolean isAllUpperCase(String name) {
        return name.toUpperCase().equals(name);
    }
}
