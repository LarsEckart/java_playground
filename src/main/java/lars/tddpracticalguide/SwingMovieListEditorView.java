package lars.tddpracticalguide;

import java.awt.FlowLayout;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class SwingMovieListEditorView extends JFrame implements MovieListEditorView {

    private JList<Movie> movieList;

    public SwingMovieListEditorView() {
        super();
    }

    @Override
    public void setMovies(Vector<Movie> movies) {
        movieList.setListData(movies);
    }

    public void init() {
        setTitle("Movie List");
        getContentPane().setLayout(new FlowLayout());
        movieList = new JList<>(new Vector<>());
        JScrollPane scroller =
                new JScrollPane(movieList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(scroller);
        pack();
    }

    public static void start() {
        SwingMovieListEditorView window = new SwingMovieListEditorView();
        window.init();
        window.show();
    }
}
