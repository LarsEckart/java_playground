package lars.tddpracticalguide;

import java.util.Vector;

public interface MovieListEditorView {

    void setMovies(Vector<Movie> movies);

    String getNewName();
}
