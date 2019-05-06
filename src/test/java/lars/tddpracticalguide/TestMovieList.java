package lars.tddpracticalguide;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TestMovieList {

    private MovieList movieList;
    private Movie starWars;
    private Movie starTrek;

    @BeforeEach
    void setUp() {
        movieList = new MovieList();
        starWars = new Movie();
        starTrek = new Movie();
    }

    @Test
    void empty_list_size_should_be_zero() {
        assertThat(movieList.size()).isEqualTo(0);
    }

    @Test
    void list_has_size_of_one_after_adding_one_movie() {
        movieList.add(starWars);
        assertThat(movieList.size()).isEqualTo(1);
    }

    @Test
    void list_has_size_of_two_after_adding_two_movies() {
        movieList.add(starWars);
        movieList.add(starTrek);
        assertThat(movieList.size()).isEqualTo(2);
    }
}
