package lars.katas;

public class Greeter {

    public String greet(String name) {
        if (name == null) {
            name = "my friend";
        }
        if (isAllUpperCase(name)) {
            return String.format("HELLO %s!", name.toUpperCase());
        }
        return String.format("Hello, %s.", name);
    }

    private boolean isAllUpperCase(String name) {
        return name.toUpperCase().equals(name);
    }
}
