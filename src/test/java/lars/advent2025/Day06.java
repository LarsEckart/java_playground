package lars.advent2025;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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

    assertThat(result).isGreaterThan(0);
  }

  // Domain objects

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
      List<String> lines = input.lines().toList();
      if (lines.isEmpty()) {
        return new Worksheet(List.of());
      }

      // Find the width of the worksheet (max line length)
      int width = lines.stream().mapToInt(String::length).max().orElse(0);

      // Pad all lines to same width
      List<String> paddedLines =
          lines.stream().map(line -> String.format("%-" + width + "s", line)).toList();

      // The last line contains operators, other lines contain numbers
      String operatorLine = paddedLines.getLast();
      List<String> numberLines = paddedLines.subList(0, paddedLines.size() - 1);

      // Parse column by column, grouping into problems
      List<Problem> problems = new ArrayList<>();
      int col = 0;

      while (col < width) {
        // Skip separator columns (all spaces)
        if (isSpaceColumn(paddedLines, col)) {
          col++;
          continue;
        }

        // Find the end of this problem (next all-space column or end)
        int startCol = col;
        while (col < width && !isSpaceColumn(paddedLines, col)) {
          col++;
        }
        int endCol = col;

        // Extract numbers from this problem's columns
        List<Long> numbers = new ArrayList<>();
        for (String line : numberLines) {
          String segment = line.substring(startCol, Math.min(endCol, line.length())).trim();
          if (!segment.isEmpty()) {
            numbers.add(Long.parseLong(segment));
          }
        }

        // Extract operator
        char operator =
            operatorLine
                .substring(startCol, Math.min(endCol, operatorLine.length()))
                .trim()
                .charAt(0);

        problems.add(new Problem(numbers, operator));
      }

      return new Worksheet(problems);
    }

    private static boolean isSpaceColumn(List<String> lines, int col) {
      for (String line : lines) {
        if (col < line.length() && line.charAt(col) != ' ') {
          return false;
        }
      }
      return true;
    }

    long grandTotal() {
      return problems.stream().mapToLong(Problem::solve).sum();
    }
  }
}
