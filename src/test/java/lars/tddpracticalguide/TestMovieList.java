package lars.tddpracticalguide;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TestMovieList {

    @Test
    void empty_list_size_should_be_zero() {
        MovieList emptyList = new MovieList();
        assertThat(emptyList.size()).isEqualTo(0);
    }

    @Test
    void list_has_size_of_one_after_adding_one_movie() {
        Movie starWars = new Movie();
        MovieList oneItemList = new MovieList();
        oneItemList.add(starWars);
        assertThat(oneItemList.size()).isEqualTo(1);
    }

    @Test
    void list_has_size_of_two_after_adding_two_movies() {
        Movie starWars = new Movie();
        Movie starTrek = new Movie();
        MovieList oneItemList = new MovieList();
        oneItemList.add(starWars);
        oneItemList.add(starTrek);
        assertThat(oneItemList.size()).isEqualTo(2);
    }
}
