package lars.tddpracticalguide;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JListOperator;

import java.util.Vector;
import javax.swing.ListModel;

import static org.assertj.core.api.Assertions.assertThat;

class TestSwingMovieListEditorView {

    JFrameOperator mainWindow;
    private MovieList movieList;
    private Movie starWars;
    private Movie starTrek;
    private Movie starGate;
    private Vector<Object> movies;

    @BeforeEach
    void setUp() {
        SwingMovieListEditorView.start();
        movieList = new MovieList();
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
    void name() {
        mainWindow = new JFrameOperator("Movie List");
        MovieListEditor editor = new MovieListEditor(movieList, (SwingMovieListEditorView) mainWindow.getWindow());

        JListOperator movieList = new JListOperator(mainWindow);
        ListModel listModel = movieList.getModel();
        assertThat(listModel.getSize()).isEqualTo(movies.size());
        for (int i = 0; i < movies.size(); i++) {
            assertThat(movies.get(i)).isEqualTo(listModel.getElementAt(i));
        }
    }
}
