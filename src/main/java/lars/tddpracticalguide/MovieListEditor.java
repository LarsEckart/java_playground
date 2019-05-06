package lars.tddpracticalguide;

import java.util.Vector;

public class MovieListEditor {

    private final MovieList movieList;
    private final MovieListEditorView aView;

    public MovieListEditor(MovieList movieList, MovieListEditorView aView) {
        this.movieList = movieList;
        this.aView = aView;
        aView.setMovies(new Vector<>(movieList.getMovies()));
    }

    public void add() {
        Movie newMovie = new Movie(aView.getNewName());
        movieList.add(newMovie);
        aView.setMovies(new Vector<>(movieList.getMovies()));
    }
}
