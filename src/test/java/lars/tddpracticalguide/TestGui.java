package lars.tddpracticalguide;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestGui {

    private MovieListEditorView mockView = mock(MovieListEditorView.class);
    private Vector<Movie> movies;
    private Movie starWars;
    private Movie starTrek;
    private Movie starGate;
    private MovieList movieList;

    @BeforeEach
    void setUp() {
        starWars = new Movie("Star Wars");
        starTrek = new Movie("Star Trek");
        starGate = new Movie("Stargate");

        movies = new Vector<>();
        movies.add(starWars);
        movies.add(starTrek);
        movies.add(starGate);

        movieList = new MovieList();
        movieList.add(starWars);
        movieList.add(starTrek);
        movieList.add(starGate);
    }

    @Test
    void test_list() {
        MovieListEditor editor = new MovieListEditor(movieList, mockView);
        verify(mockView).setMovies(movies);
    }
}
