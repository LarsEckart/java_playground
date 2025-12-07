package lars.advent2025;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

class Day07 {

  static final String EXAMPLE =
      """
      .......S.......
      ...............
      .......^.......
      ...............
      ......^.^......
      ...............
      .....^.^.^.....
      ...............
      ....^.^...^....
      ...............
      ...^.^...^.^...
      ...............
      ..^...^.....^..
      ...............
      .^.^.^.^.^...^.
      ...............
      """;

  @Nested
  class PartOne {

    @Test
    void findStartPosition() {
      Manifold manifold = Manifold.parse(EXAMPLE);

      assertThat(manifold.startColumn()).isEqualTo(7);
    }

    @Test
    void singleSplitter() {
      String input =
          """
          ..S..
          .....
          ..^..
          .....
          """;
      Manifold manifold = Manifold.parse(input);

      assertThat(manifold.countSplits()).isEqualTo(1);
    }

    @Test
    void beamExitsWithoutSplitting() {
      String input =
          """
          ..S..
          .....
          .....
          """;
      Manifold manifold = Manifold.parse(input);

      assertThat(manifold.countSplits()).isEqualTo(0);
    }

    @Test
    void twoSplittersInLine() {
      String input =
          """
          ..S..
          .....
          ..^..
          .....
          .^.^.
          .....
          """;
      Manifold manifold = Manifold.parse(input);

      // First splitter creates 2 beams (1 split)
      // Each of those hits a splitter (2 more splits)
      assertThat(manifold.countSplits()).isEqualTo(3);
    }

    @Test
    void mergingBeams_countedOnce() {
      // When two beams merge into same column, they become one beam
      String input =
          """
          ..S..
          .....
          ..^..
          .....
          .^.^.
          .....
          ..^..
          .....
          """;
      Manifold manifold = Manifold.parse(input);

      // Row 2: 1 split -> 2 beams at cols 1,3
      // Row 4: 2 splits -> beams at cols 0,2 and 2,4 (col 2 merges) -> 3 beams
      // Row 6: col 2 has splitter, hit by 1 beam -> 1 split
      assertThat(manifold.countSplits()).isEqualTo(4);
    }

    @Test
    void example() {
      Manifold manifold = Manifold.parse(EXAMPLE);

      assertThat(manifold.countSplits()).isEqualTo(21);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = ".*")
    @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = ".*")
    void puzzleInput() throws Exception {
      Path inputPath = AdventInputs.ensureDayInput(2025, 7);
      String input = Files.readString(inputPath);

      Manifold manifold = Manifold.parse(input);
      long result = manifold.countSplits();
      System.out.println("Day 7 Part 1: " + result);

      assertThat(result).isGreaterThan(0);
    }
  }

  // Domain objects

  record Manifold(List<String> rows, int startColumn) {

    static Manifold parse(String input) {
      List<String> rows = input.lines().toList();
      int startColumn = -1;

      for (String row : rows) {
        int idx = row.indexOf('S');
        if (idx >= 0) {
          startColumn = idx;
          break;
        }
      }

      return new Manifold(rows, startColumn);
    }

    int width() {
      return rows.isEmpty() ? 0 : rows.getFirst().length();
    }

    int height() {
      return rows.size();
    }

    char charAt(int row, int col) {
      if (row < 0 || row >= height() || col < 0 || col >= width()) {
        return ' '; // out of bounds
      }
      return rows.get(row).charAt(col);
    }

    boolean isSplitter(int row, int col) {
      return charAt(row, col) == '^';
    }

    long countSplits() {
      long totalSplits = 0;

      // Track active beam columns using a set (to handle merging)
      // We use a boolean array for efficiency
      boolean[] activeBeams = new boolean[width()];
      activeBeams[startColumn] = true;

      // Process row by row, starting from after S
      for (int row = 1; row < height(); row++) {
        // Find splitters hit by active beams
        Deque<Integer> splitterColumns = new ArrayDeque<>();
        for (int col = 0; col < width(); col++) {
          if (activeBeams[col] && isSplitter(row, col)) {
            splitterColumns.add(col);
          }
        }

        // Process each splitter hit
        for (int splitterCol : splitterColumns) {
          totalSplits++;
          // Stop the beam at the splitter
          activeBeams[splitterCol] = false;
          // Emit new beams left and right (if in bounds)
          if (splitterCol > 0) {
            activeBeams[splitterCol - 1] = true;
          }
          if (splitterCol < width() - 1) {
            activeBeams[splitterCol + 1] = true;
          }
        }

        // Beams that went out of bounds are removed (handled by bounds checking)
        // Clean up beams that exited left or right edge - already handled since
        // we only set valid indices
      }

      return totalSplits;
    }
  }
}
