package lars.tddpracticalguide;

import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestGui {

  private MovieListEditorView mockView;
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
    mockView = mock(MovieListEditorView.class);
  }

  @Test
  void test_list() {
    new MovieListEditor(movieList, mockView);
    verify(mockView).setMovies(movies);
  }

  @Test
  void adding() {
    String LOST_IN_SPACE = "Lost in Space";
    Movie lostInSpace = new Movie(LOST_IN_SPACE);
    Vector<Movie> moviesWithAddition = new Vector<>(movies);
    moviesWithAddition.add(lostInSpace);

    given(mockView.getNewName()).willReturn(LOST_IN_SPACE);

    MovieListEditor editor = new MovieListEditor(movieList, mockView);
    editor.add();

    assertAll(
        () -> verify(mockView).setMovies(movies),
        () -> verify(mockView).getNewName(),
        () -> verify(mockView).setMovies(moviesWithAddition));
  }
}
