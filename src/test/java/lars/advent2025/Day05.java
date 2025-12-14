package lars.advent2025;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.guava.labs.collect.BinarySearch;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lars.advent.PuzzleInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

class Day05 {

  static final String EXAMPLE =
      """
      3-5
      10-14
      16-20
      12-18

      1
      5
      8
      11
      17
      32
      """;

  @Test
  void ingredient_isFresh_whenInRange() {
    IngredientDatabase db = IngredientDatabase.parse(EXAMPLE);

    assertThat(db.isFresh(5)).isTrue(); // in range 3-5
    assertThat(db.isFresh(11)).isTrue(); // in range 10-14
    assertThat(db.isFresh(17)).isTrue(); // in ranges 16-20 and 12-18
  }

  @Test
  void ingredient_isSpoiled_whenNotInAnyRange() {
    IngredientDatabase db = IngredientDatabase.parse(EXAMPLE);

    assertThat(db.isFresh(1)).isFalse();
    assertThat(db.isFresh(8)).isFalse();
    assertThat(db.isFresh(32)).isFalse();
  }

  @Test
  void example() {
    IngredientDatabase db = IngredientDatabase.parse(EXAMPLE);

    assertThat(db.countFreshIngredients()).isEqualTo(3);
  }

  @Test
  void examplePart2() {
    IngredientDatabase db = IngredientDatabase.parse(EXAMPLE);

    assertThat(db.countAllFreshIds()).isEqualTo(14);
  }

  @Test
  @DisabledIfEnvironmentVariable(named = "CI", matches = ".*")
  @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = ".*")
  void puzzleInput() throws Exception {
    Path inputPath = PuzzleInput.forDate(2025, 5);
    String input = Files.readString(inputPath);

    IngredientDatabase db = IngredientDatabase.parse(input);
    long result = db.countFreshIngredients();
    long result2 = db.countAllFreshIds();
    System.out.println("Day 5 Part 1: " + result);
    System.out.println("Day 5 Part 2: " + result2);

    assertThat(result).isEqualTo(773);
    assertThat(result2).isGreaterThan(0);
  }

  // Domain objects

  record Range(long start, long end) {
    boolean contains(long id) {
      return id >= start && id <= end;
    }
  }

  record IngredientDatabase(List<Range> mergedRanges, List<Long> availableIngredients) {

    static IngredientDatabase parse(String input) {
      String[] sections = input.split("\n\n");

      List<Range> ranges = new ArrayList<>();
      for (String line : sections[0].lines().toList()) {
        if (line.isBlank()) continue;
        String[] parts = line.split("-");
        ranges.add(new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
      }

      List<Long> ingredients = new ArrayList<>();
      for (String line : sections[1].lines().toList()) {
        if (line.isBlank()) continue;
        ingredients.add(Long.parseLong(line.trim()));
      }

      List<Range> merged = mergeRanges(ranges);
      return new IngredientDatabase(merged, ingredients);
    }

    private static List<Range> mergeRanges(List<Range> ranges) {
      List<Range> sorted = ranges.stream().sorted(Comparator.comparingLong(Range::start)).toList();

      List<Range> merged = new ArrayList<>();
      for (Range range : sorted) {
        if (merged.isEmpty()) {
          merged.add(range);
        } else {
          Range last = merged.getLast();
          if (last.end() >= range.start() - 1) {
            merged.set(
                merged.size() - 1, new Range(last.start(), Math.max(last.end(), range.end())));
          } else {
            merged.add(range);
          }
        }
      }
      return merged;
    }

    boolean isFresh(long ingredientId) {
      // Binary search to find the range that could contain this ingredient
      // We search by range start - find the last range whose start <= ingredientId
      var insertionPoint =
          BinarySearch.inSortedList(mergedRanges, Range::start).insertionPointFor(ingredientId);

      // Check the floor (largest range with start <= ingredientId)
      int floorIndex = insertionPoint.floor();
      if (floorIndex >= 0 && floorIndex < mergedRanges.size()) {
        Range range = mergedRanges.get(floorIndex);
        if (range.contains(ingredientId)) {
          return true;
        }
      }
      return false;
    }

    long countFreshIngredients() {
      return availableIngredients.stream().filter(this::isFresh).count();
    }

    long countAllFreshIds() {
      return mergedRanges.stream().mapToLong(r -> r.end() - r.start() + 1).sum();
    }
  }
}
