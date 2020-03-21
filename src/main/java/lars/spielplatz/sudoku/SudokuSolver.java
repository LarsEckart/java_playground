package lars.spielplatz.sudoku;

import java.util.ArrayList;
import java.util.List;

public class SudokuSolver {

  private List<Grid> solutions = new ArrayList<>();

  public SudokuSolver(int[][] numbers) {
    solve(Grid.create(numbers), solutions);
  }

  public SudokuSolver(Grid grid) {
    solve(grid, solutions);
  }

  public boolean isSolveable() {
    return !solutions.isEmpty();
  }

  public boolean isUniqueSolution() {
    return solutions.size() == 1;
  }

  public int[][] getSolution() {
    int[] cells = solutions.get(0).getCells();
    int k = 0;
    int[][] numbers = new int[9][9];
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        numbers[i][j] = cells[k];
        k++;
      }
    }
    return numbers;
  }

  public List<Grid> getSolutions() {
    return solutions;
  }

  // Recursive routine that implements the bifurcation algorithm
  private static void solve(Grid grid, List<Grid> solutions) {
    // Return if there is already a solution
    if (solutions.size() >= 2) {
      return;
    }

    // Find first empty cell
    int loc = grid.findEmptyCell();

    // If no empty cells are found, a solution is found
    if (loc < 0) {
      solutions.add(grid.clone());
      return;
    }

    // Try each of the 9 digits in this empty cell
    for (int n = 1; n < 10; n++) {
      if (grid.set(loc, n)) {
        // With this cell set, work on the next cell
        solve(grid, solutions);

        // Clear the cell so that it can be filled with another digit
        grid.clear(loc);
      }
    }
  }
}
