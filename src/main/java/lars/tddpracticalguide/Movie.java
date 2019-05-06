package lars.tddpracticalguide;

public class Movie {

    private final String name;

    public Movie(String name) {

        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
