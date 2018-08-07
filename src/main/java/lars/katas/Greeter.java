package lars.katas;

public class Greeter {

    public String greet(String name) {
        if (name == null) {
            name = "my friend";
        }
        if (name.toUpperCase().equals(name)) {
            return String.format("HELLO %s!", name.toUpperCase());
        }
        return String.format("Hello, %s.", name);
    }
}
