package lars.tddpracticalguide;

public class MovieList {

    private int numberOfMovies;

    public int size() {
        return numberOfMovies;
    }

    public void add(Movie movie) {
        numberOfMovies++;
    }
}
