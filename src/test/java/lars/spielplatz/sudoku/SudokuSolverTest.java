package lars.spielplatz.sudoku;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class SudokuSolverTest {

  @Test
  void exploration() throws IOException, URISyntaxException {
    // TODO: rewrite, temp directiory, sudokus as string with helper method or so
    java.net.URL url = this.getClass().getClassLoader().getResource("sudoku.txt");
    java.nio.file.Path resPath = java.nio.file.Paths.get(url.toURI());

    Grid grid = Grid.create(new FileReader(resPath.toFile(), StandardCharsets.UTF_8));
    SudokuSolver sudokuSolver = new SudokuSolver(grid);

    sudokuSolver.getSolutions().forEach(System.out::println);

    assertThat(sudokuSolver.isSolveable()).isTrue();
  }
}
