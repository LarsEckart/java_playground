package lars.advent2025;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lars.advent.PuzzleInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

class Day04 {

  static final String EXAMPLE =
      """
      ..@@.@@@@.
      @@@.@.@.@@
      @@@@@.@.@@
      @.@@@@..@.
      @@.@@@@.@@
      .@@@@@@@.@
      .@.@.@.@@@
      @.@@@.@@@@
      .@@@@@@@@.
      @.@.@@@.@.
      """;

  @Test
  void paperRoll_isAccessible_withFewerThanFourNeighbors() {
    Warehouse warehouse = Warehouse.parse(EXAMPLE);

    // Top-left accessible rolls (from the example: x marks at row 0)
    assertThat(warehouse.isAccessible(0, 2)).isTrue(); // first x
    assertThat(warehouse.isAccessible(0, 3)).isTrue(); // second x
    assertThat(warehouse.isAccessible(0, 5)).isTrue(); // third x
    assertThat(warehouse.isAccessible(0, 6)).isTrue(); // fourth x
    assertThat(warehouse.isAccessible(0, 8)).isTrue(); // fifth x
  }

  @Test
  void paperRoll_isNotAccessible_withFourOrMoreNeighbors() {
    Warehouse warehouse = Warehouse.parse(EXAMPLE);

    // The @ at position (0,7) has neighbors and should NOT be accessible
    assertThat(warehouse.isAccessible(0, 7)).isFalse();
  }

  @Test
  void emptyCell_isNeverAccessible() {
    Warehouse warehouse = Warehouse.parse(EXAMPLE);

    // Empty cells (.) should not be counted
    assertThat(warehouse.isAccessible(0, 0)).isFalse();
    assertThat(warehouse.isAccessible(0, 1)).isFalse();
  }

  @Test
  void example() {
    Warehouse warehouse = Warehouse.parse(EXAMPLE);

    assertThat(warehouse.countAccessibleRolls()).isEqualTo(13);
  }

  @Test
  void examplePart2() {
    Warehouse warehouse = Warehouse.parse(EXAMPLE);

    assertThat(warehouse.totalRemovableRolls()).isEqualTo(43);
  }

  @Test
  @DisabledIfEnvironmentVariable(named = "CI", matches = ".*")
  @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = ".*")
  void puzzleInput() throws Exception {
    Path inputPath = PuzzleInput.forDate(2025, 4);
    String input = Files.readString(inputPath);

    Warehouse warehouse = Warehouse.parse(input);
    long result = warehouse.countAccessibleRolls();
    long result2 = warehouse.totalRemovableRolls();
    System.out.println("Day 4 Part 1: " + result);
    System.out.println("Day 4 Part 2: " + result2);

    assertThat(result).isEqualTo(1397);
    assertThat(result2).isEqualTo(8758);
  }

  // Domain objects

  record Warehouse(List<String> grid) {

    static Warehouse parse(String input) {
      List<String> rows = input.lines().filter(line -> !line.isBlank()).toList();
      return new Warehouse(rows);
    }

    boolean isAccessible(int row, int col) {
      // Must be a paper roll (@) first
      if (!isPaperRoll(row, col)) {
        return false;
      }

      // Count adjacent paper rolls (8 directions)
      int neighborCount = countAdjacentRolls(row, col);

      // Accessible if fewer than 4 neighbors
      return neighborCount < 4;
    }

    private boolean isPaperRoll(int row, int col) {
      if (row < 0 || row >= grid.size()) {
        return false;
      }
      String rowStr = grid.get(row);
      if (col < 0 || col >= rowStr.length()) {
        return false;
      }
      return rowStr.charAt(col) == '@';
    }

    private int countAdjacentRolls(int row, int col) {
      int count = 0;
      // Check all 8 directions
      for (int dr = -1; dr <= 1; dr++) {
        for (int dc = -1; dc <= 1; dc++) {
          if (dr == 0 && dc == 0) {
            continue; // Skip the cell itself
          }
          if (isPaperRoll(row + dr, col + dc)) {
            count++;
          }
        }
      }
      return count;
    }

    long countAccessibleRolls() {
      long count = 0;
      for (int row = 0; row < grid.size(); row++) {
        String rowStr = grid.get(row);
        for (int col = 0; col < rowStr.length(); col++) {
          if (isAccessible(row, col)) {
            count++;
          }
        }
      }
      return count;
    }

    long totalRemovableRolls() {
      // Work with a mutable copy of the grid
      char[][] mutableGrid = new char[grid.size()][];
      for (int i = 0; i < grid.size(); i++) {
        mutableGrid[i] = grid.get(i).toCharArray();
      }

      // Initial scan: find all paper roll positions as candidates
      Set<Coordinate> candidates = new HashSet<>();
      for (int row = 0; row < mutableGrid.length; row++) {
        for (int col = 0; col < mutableGrid[row].length; col++) {
          if (mutableGrid[row][col] == '@') {
            candidates.add(new Coordinate(row, col));
          }
        }
      }

      long totalRemoved = 0;

      while (!candidates.isEmpty()) {
        // Find accessible rolls among candidates
        List<Coordinate> toRemove = new ArrayList<>();
        for (Coordinate coord : candidates) {
          if (isAccessibleInGrid(mutableGrid, coord.row(), coord.col())) {
            toRemove.add(coord);
          }
        }

        if (toRemove.isEmpty()) {
          break;
        }

        // Remove accessible rolls and collect their neighbors as next candidates
        Set<Coordinate> nextCandidates = new HashSet<>();
        for (Coordinate coord : toRemove) {
          mutableGrid[coord.row()][coord.col()] = '.';

          // Only neighbors of removed cells might become accessible
          for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
              if (dr == 0 && dc == 0) continue;
              int nr = coord.row() + dr, nc = coord.col() + dc;
              if (isPaperRollInGrid(mutableGrid, nr, nc)) {
                nextCandidates.add(new Coordinate(nr, nc));
              }
            }
          }
        }

        totalRemoved += toRemove.size();
        candidates = nextCandidates;
      }

      return totalRemoved;
    }

    record Coordinate(int row, int col) {}

    private boolean isAccessibleInGrid(char[][] grid, int row, int col) {
      if (!isPaperRollInGrid(grid, row, col)) {
        return false;
      }
      return countAdjacentRollsInGrid(grid, row, col) < 4;
    }

    private boolean isPaperRollInGrid(char[][] grid, int row, int col) {
      if (row < 0 || row >= grid.length) {
        return false;
      }
      if (col < 0 || col >= grid[row].length) {
        return false;
      }
      return grid[row][col] == '@';
    }

    private int countAdjacentRollsInGrid(char[][] grid, int row, int col) {
      int count = 0;
      for (int dr = -1; dr <= 1; dr++) {
        for (int dc = -1; dc <= 1; dc++) {
          if (dr == 0 && dc == 0) {
            continue;
          }
          if (isPaperRollInGrid(grid, row + dr, col + dc)) {
            if (++count >= 4) return count; // Early exit - already inaccessible
          }
        }
      }
      return count;
    }
  }
}
