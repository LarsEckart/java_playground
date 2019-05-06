package lars.tddpracticalguide;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TestEmptyMovieList {

    private MovieList movieList;

    @BeforeEach
    void setUp() {
        movieList = new MovieList();
    }

    @Test
    void empty_list_size_should_be_zero() {
        assertThat(movieList.size()).isEqualTo(0);
    }
}
