package lars.katas;

public class Greeter {

    public String greet(String name) {
        if (name == null) {
            name = "my friend";
        }
        return String.format("Hello, %s.", name);
    }
}
