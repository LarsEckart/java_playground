package lars.advent2025;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

class Day06 {

  static final String EXAMPLE =
      """
      123 328  51 64\s
       45 64  387 23\s
        6 98  215 314
      *   +   *   +
      """;

  @Nested
  class PartOne {

    @Test
    void parseProblems_fromWorksheet() {
      Worksheet worksheet = Worksheet.parse(EXAMPLE);

      assertThat(worksheet.problems()).hasSize(4);
      assertThat(worksheet.problems().get(0)).isEqualTo(new Problem(List.of(123L, 45L, 6L), '*'));
      assertThat(worksheet.problems().get(1)).isEqualTo(new Problem(List.of(328L, 64L, 98L), '+'));
      assertThat(worksheet.problems().get(2)).isEqualTo(new Problem(List.of(51L, 387L, 215L), '*'));
      assertThat(worksheet.problems().get(3)).isEqualTo(new Problem(List.of(64L, 23L, 314L), '+'));
    }

    @Test
    void problem_solves_multiplication() {
      Problem problem = new Problem(List.of(123L, 45L, 6L), '*');

      assertThat(problem.solve()).isEqualTo(33210L);
    }

    @Test
    void problem_solves_addition() {
      Problem problem = new Problem(List.of(328L, 64L, 98L), '+');

      assertThat(problem.solve()).isEqualTo(490L);
    }

    @Test
    void example() {
      Worksheet worksheet = Worksheet.parse(EXAMPLE);

      assertThat(worksheet.grandTotal()).isEqualTo(4277556L);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = ".*")
    @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = ".*")
    void puzzleInput() throws Exception {
      Path inputPath = AdventInputs.ensureDayInput(2025, 6);
      String input = Files.readString(inputPath);

      Worksheet worksheet = Worksheet.parse(input);
      long result = worksheet.grandTotal();
      System.out.println("Day 6 Part 1: " + result);

      assertThat(result).isEqualTo(6757749566978L);
    }
  }

  @Nested
  class PartTwo {

    @Test
    void parseCephalopodProblems_fromWorksheet() {
      CephalopodWorksheet worksheet = CephalopodWorksheet.parse(EXAMPLE);

      assertThat(worksheet.problems()).hasSize(4);
      // Right-to-left, each column is a number read top-to-bottom
      assertThat(worksheet.problems().get(0)).isEqualTo(new Problem(List.of(4L, 431L, 623L), '+'));
      assertThat(worksheet.problems().get(1)).isEqualTo(new Problem(List.of(175L, 581L, 32L), '*'));
      assertThat(worksheet.problems().get(2)).isEqualTo(new Problem(List.of(8L, 248L, 369L), '+'));
      assertThat(worksheet.problems().get(3)).isEqualTo(new Problem(List.of(356L, 24L, 1L), '*'));
    }

    @Test
    void example() {
      CephalopodWorksheet worksheet = CephalopodWorksheet.parse(EXAMPLE);
      assertThat(worksheet.grandTotal()).isEqualTo(3263827L);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = ".*")
    @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = ".*")
    void puzzleInput() throws Exception {
      Path inputPath = AdventInputs.ensureDayInput(2025, 6);
      String input = Files.readString(inputPath);

      CephalopodWorksheet worksheet = CephalopodWorksheet.parse(input);
      long result = worksheet.grandTotal();
      System.out.println("Day 6 Part 2: " + result);

      assertThat(result).isGreaterThan(0);
    }
  }

  // Domain objects

  record TextGrid(List<String> rows) {

    static TextGrid parse(String input) {
      List<String> lines = input.lines().toList();
      if (lines.isEmpty()) {
        return new TextGrid(List.of());
      }

      int width = lines.stream().mapToInt(String::length).max().orElse(0);
      List<String> padded =
          lines.stream().map(line -> String.format("%-" + width + "s", line)).toList();

      return new TextGrid(padded);
    }

    int width() {
      return rows.isEmpty() ? 0 : rows.getFirst().length();
    }

    int height() {
      return rows.size();
    }

    int operatorRow() {
      return height() - 1;
    }

    char charAt(int row, int col) {
      return rows.get(row).charAt(col);
    }

    boolean isSpaceColumn(int col) {
      for (String row : rows) {
        if (row.charAt(col) != ' ') {
          return false;
        }
      }
      return true;
    }

    String rowSegment(int row, int startCol, int endCol) {
      return rows.get(row).substring(startCol, endCol);
    }

    /** Returns column content for rows 0 to height-2 (excluding last row). */
    String columnDigits(int col) {
      StringBuilder sb = new StringBuilder();
      for (int row = 0; row < height() - 1; row++) {
        char ch = charAt(row, col);
        if (ch != ' ') {
          sb.append(ch);
        }
      }
      return sb.toString();
    }
  }

  record Problem(List<Long> numbers, char operator) {
    long solve() {
      if (operator == '+') {
        return numbers.stream().mapToLong(Long::longValue).sum();
      } else {
        return numbers.stream().mapToLong(Long::longValue).reduce(1L, (a, b) -> a * b);
      }
    }
  }

  record Worksheet(List<Problem> problems) {

    static Worksheet parse(String input) {
      TextGrid grid = TextGrid.parse(input);
      if (grid.height() == 0) {
        return new Worksheet(List.of());
      }

      List<Problem> problems = new ArrayList<>();
      int col = 0;

      while (col < grid.width()) {
        if (grid.isSpaceColumn(col)) {
          col++;
          continue;
        }

        int startCol = col;
        while (col < grid.width() && !grid.isSpaceColumn(col)) {
          col++;
        }
        int endCol = col;

        List<Long> numbers = new ArrayList<>();
        for (int row = 0; row < grid.operatorRow(); row++) {
          String segment = grid.rowSegment(row, startCol, endCol).trim();
          if (!segment.isEmpty()) {
            numbers.add(Long.parseLong(segment));
          }
        }

        char operator = grid.rowSegment(grid.operatorRow(), startCol, endCol).trim().charAt(0);
        problems.add(new Problem(numbers, operator));
      }

      return new Worksheet(problems);
    }

    long grandTotal() {
      return problems.stream().mapToLong(Problem::solve).sum();
    }
  }

  record CephalopodWorksheet(List<Problem> problems) {

    static CephalopodWorksheet parse(String input) {
      TextGrid grid = TextGrid.parse(input);
      if (grid.height() == 0) {
        return new CephalopodWorksheet(List.of());
      }

      List<Problem> problems = new ArrayList<>();
      int col = grid.width() - 1;

      while (col >= 0) {
        if (grid.isSpaceColumn(col)) {
          col--;
          continue;
        }

        int endCol = col;
        while (col >= 0 && !grid.isSpaceColumn(col)) {
          col--;
        }
        int startCol = col + 1;

        // Each column is a number (digits read top-to-bottom), read right-to-left
        List<Long> numbers = new ArrayList<>();
        for (int c = endCol; c >= startCol; c--) {
          String digits = grid.columnDigits(c);
          if (!digits.isEmpty()) {
            numbers.add(Long.parseLong(digits));
          }
        }

        // Find operator in this problem's columns
        char operator = ' ';
        for (int c = startCol; c <= endCol; c++) {
          char ch = grid.charAt(grid.operatorRow(), c);
          if (ch != ' ') {
            operator = ch;
            break;
          }
        }

        problems.add(new Problem(numbers, operator));
      }

      return new CephalopodWorksheet(problems);
    }

    long grandTotal() {
      return problems.stream().mapToLong(Problem::solve).sum();
    }
  }
}
