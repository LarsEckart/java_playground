package lars.advent2025;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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

      assertThat(result).isEqualTo(1518);
    }
  }

  @Nested
  class PartTwo {

    @Test
    void noSplitters_oneTimeline() {
      String input =
          """
          ..S..
          .....
          .....
          """;
      Manifold manifold = Manifold.parse(input);

      assertThat(manifold.countTimelines()).isEqualTo(1);
    }

    @Test
    void singleSplitter_twoTimelines() {
      String input =
          """
          ..S..
          .....
          ..^..
          .....
          """;
      Manifold manifold = Manifold.parse(input);

      // Particle goes left OR right -> 2 timelines
      assertThat(manifold.countTimelines()).isEqualTo(2);
    }

    @Test
    void twoSplittersInLine_fourTimelines() {
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

      // First split: 2 timelines (left, right)
      // Left timeline hits left splitter: 2 more (LL, LR)
      // Right timeline hits right splitter: 2 more (RL, RR)
      // Total: 4 timelines
      assertThat(manifold.countTimelines()).isEqualTo(4);
    }

    @Test
    void example() {
      Manifold manifold = Manifold.parse(EXAMPLE);

      assertThat(manifold.countTimelines()).isEqualTo(40);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = ".*")
    @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = ".*")
    void puzzleInput() throws Exception {
      Path inputPath = AdventInputs.ensureDayInput(2025, 7);
      String input = Files.readString(inputPath);

      Manifold manifold = Manifold.parse(input);
      long result = manifold.countTimelines();
      System.out.println("Day 7 Part 2: " + result);

      assertThat(result).isGreaterThan(0);
    }
  }

  // Domain objects

  record Grid(List<String> rows) {

    static Grid parse(String input) {
      return new Grid(input.lines().toList());
    }

    int width() {
      return rows.isEmpty() ? 0 : rows.getFirst().length();
    }

    int height() {
      return rows.size();
    }

    char charAt(int row, int col) {
      if (row < 0 || row >= height() || col < 0 || col >= width()) {
        return ' ';
      }
      return rows.get(row).charAt(col);
    }

    int findColumn(char target) {
      for (String row : rows) {
        int idx = row.indexOf(target);
        if (idx >= 0) {
          return idx;
        }
      }
      return -1;
    }

    boolean isSplitter(int row, int col) {
      return charAt(row, col) == '^';
    }
  }

  static class BeamState {
    private final boolean[] active;

    BeamState(int width) {
      this.active = new boolean[width];
    }

    void activate(int col) {
      if (col >= 0 && col < active.length) {
        active[col] = true;
      }
    }

    void deactivate(int col) {
      active[col] = false;
    }

    boolean isActive(int col) {
      return active[col];
    }

    List<Integer> activeColumns() {
      List<Integer> columns = new ArrayList<>();
      for (int col = 0; col < active.length; col++) {
        if (isActive(col)) {
          columns.add(col);
        }
      }
      return columns;
    }

    void split(int col) {
      deactivate(col);
      activate(col - 1);
      activate(col + 1);
    }
  }

  static class TimelineState {
    private long[] counts;

    TimelineState(int width) {
      this.counts = new long[width];
    }

    void add(int col, long amount) {
      if (col >= 0 && col < counts.length) {
        counts[col] += amount;
      }
    }

    long at(int col) {
      return counts[col];
    }

    int width() {
      return counts.length;
    }

    long total() {
      long sum = 0;
      for (long count : counts) {
        sum += count;
      }
      return sum;
    }

    TimelineState nextRow() {
      return new TimelineState(counts.length);
    }

    void advanceTo(TimelineState next) {
      this.counts = next.counts;
    }
  }

  record Manifold(Grid grid, int startColumn) {

    static Manifold parse(String input) {
      Grid grid = Grid.parse(input);
      int startColumn = grid.findColumn('S');
      return new Manifold(grid, startColumn);
    }

    long countSplits() {
      long totalSplits = 0;
      BeamState beams = new BeamState(grid.width());
      beams.activate(startColumn);

      for (int row = 1; row < grid.height(); row++) {
        for (int col : beams.activeColumns()) {
          if (grid.isSplitter(row, col)) {
            totalSplits++;
            beams.split(col);
          }
        }
      }

      return totalSplits;
    }

    long countTimelines() {
      TimelineState timelines = new TimelineState(grid.width());
      timelines.add(startColumn, 1);

      for (int row = 1; row < grid.height(); row++) {
        TimelineState next = timelines.nextRow();

        for (int col = 0; col < timelines.width(); col++) {
          long count = timelines.at(col);
          if (count == 0) continue;

          if (grid.isSplitter(row, col)) {
            next.add(col - 1, count);
            next.add(col + 1, count);
          } else {
            next.add(col, count);
          }
        }

        timelines.advanceTo(next);
      }

      return timelines.total();
    }
  }
}
