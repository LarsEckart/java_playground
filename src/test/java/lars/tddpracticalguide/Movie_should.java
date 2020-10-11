package lars.tddpracticalguide;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Movie_should {

  private Movie movie;

  @BeforeEach
  void setUp() {
    movie = new Movie("Star Wars");
  }

  @Test
  void have_a_name() {
    assertThat(movie.getName()).isEqualTo("Star Wars");
  }

  @Test
  void display_movie_name_for_toString() {
    assertThat(movie.toString()).isEqualTo("Star Wars");
  }

  @Test
  void bad_rating() {
    movie.addRating(5);
    movie.addRating(3);

    assertThat(movie.getAverageRating()).isEqualTo(4);
  }
}
