package lars.tddpracticalguide;

import java.util.ArrayList;
import java.util.Collection;

public class MovieList {

  private Collection<Movie> movies = new ArrayList<>();

  public int size() {
    return movies.size();
  }

  public void add(Movie movie) {
    movies.add(movie);
  }

  public boolean contains(Movie movie) {
    return movies.contains(movie);
  }

  public Collection<Movie> getMovies() {
    return this.movies;
  }
}
