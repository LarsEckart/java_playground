package ee.lars.json;

public class ImmutableBag {

    private final String name;

    public ImmutableBag(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
