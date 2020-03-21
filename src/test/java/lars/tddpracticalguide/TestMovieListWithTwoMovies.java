package lars.tddpracticalguide;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TestMovieListWithTwoMovies {

  private MovieList movieList;
  private Movie starWars;
  private Movie starTrek;
  private Movie starGate;

  @BeforeEach
  void setUp() {
    movieList = new MovieList();
    starWars = new Movie("Star Wars");
    starTrek = new Movie("Star Trek");
    starGate = new Movie("Stargate");

    movieList.add(starWars);
    movieList.add(starTrek);
  }

  @Test
  void list_has_size_of_two_after_adding_two_movies() {
    assertThat(movieList.size()).isEqualTo(2);
  }

  @Test
  void list_contains_added_movies() {
    assertThat(movieList.contains(starWars)).isTrue();
    assertThat(movieList.contains(starTrek)).isTrue();
    assertThat(movieList.contains(starGate)).isFalse();
  }
}
