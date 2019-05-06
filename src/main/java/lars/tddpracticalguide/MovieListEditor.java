package lars.tddpracticalguide;

import java.util.Vector;

public class MovieListEditor {

    public MovieListEditor(MovieList movieList, MovieListEditorView aView) {
        aView.setMovies(new Vector(movieList.getMovies()));
    }
}
