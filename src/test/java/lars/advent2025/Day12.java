package lars.advent2025;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lars.advent.PuzzleInput;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

class Day12 {

  static final String EXAMPLE =
      """
      0:
      ###
      ##.
      ##.

      1:
      ###
      ##.
      .##

      2:
      .##
      ###
      ##.

      3:
      ##.
      ###
      ##.

      4:
      ###
      #..
      ###

      5:
      ###
      .#.
      ###

      4x4: 0 0 0 0 2 0
      12x5: 1 0 1 0 2 2
      12x5: 1 0 1 0 3 2
      """;

  @Nested
  class PartOne {

    @Test
    void parseShapes() {
      PuzzleData data = PuzzleData.parse(EXAMPLE);

      assertThat(data.shapes()).hasSize(6);
      assertThat(data.shapes().get(0).cellCount()).isEqualTo(7);
      assertThat(data.shapes().get(4).cellCount()).isEqualTo(7);
    }

    @Test
    void parseRegions() {
      PuzzleData data = PuzzleData.parse(EXAMPLE);

      assertThat(data.regions()).hasSize(3);
      assertThat(data.regions().get(0).width()).isEqualTo(4);
      assertThat(data.regions().get(0).height()).isEqualTo(4);
      assertThat(data.regions().get(0).presentCounts()).containsExactly(0, 0, 0, 0, 2, 0);
    }

    @Test
    void firstRegionCanFit() {
      PuzzleData data = PuzzleData.parse(EXAMPLE);
      Region region = data.regions().get(0);

      boolean canFit = region.canFitAllPresents(data.shapes());

      assertThat(canFit).isTrue();
    }

    @Test
    void secondRegionCanFit() {
      PuzzleData data = PuzzleData.parse(EXAMPLE);
      Region region = data.regions().get(1);

      boolean canFit = region.canFitAllPresents(data.shapes());

      assertThat(canFit).isTrue();
    }

    @Test
    void thirdRegionCannotFit() {
      PuzzleData data = PuzzleData.parse(EXAMPLE);
      Region region = data.regions().get(2);

      boolean canFit = region.canFitAllPresents(data.shapes());

      assertThat(canFit).isFalse();
    }

    @Test
    void countFittingRegionsInExample() {
      PuzzleData data = PuzzleData.parse(EXAMPLE);

      long count = data.countFittingRegions();

      assertThat(count).isEqualTo(2);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = ".*")
    @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = ".*")
    void puzzleInput() throws Exception {
      Path inputPath = PuzzleInput.forDate(2025, 12);
      String input = Files.readString(inputPath);

      PuzzleData data = PuzzleData.parse(input);
      long result = data.countFittingRegions();
      System.out.println("Day 12 Part 1: " + result);

      assertThat(result).isEqualTo(528);
    }
  }

  // Domain objects

  record Cell(int row, int col) {}

  record Shape(Set<Cell> cells) {

    static Shape fromLines(List<String> lines) {
      Set<Cell> cells = new HashSet<>();
      for (int row = 0; row < lines.size(); row++) {
        String line = lines.get(row);
        for (int col = 0; col < line.length(); col++) {
          if (line.charAt(col) == '#') {
            cells.add(new Cell(row, col));
          }
        }
      }
      return new Shape(cells);
    }

    int cellCount() {
      return cells.size();
    }

    Shape normalize() {
      int minRow = cells.stream().mapToInt(Cell::row).min().orElse(0);
      int minCol = cells.stream().mapToInt(Cell::col).min().orElse(0);
      Set<Cell> normalized = new HashSet<>();
      for (Cell c : cells) {
        normalized.add(new Cell(c.row() - minRow, c.col() - minCol));
      }
      return new Shape(normalized);
    }

    Shape rotate90() {
      Set<Cell> rotated = new HashSet<>();
      for (Cell c : cells) {
        rotated.add(new Cell(c.col(), -c.row()));
      }
      return new Shape(rotated).normalize();
    }

    Shape flipHorizontal() {
      Set<Cell> flipped = new HashSet<>();
      for (Cell c : cells) {
        flipped.add(new Cell(c.row(), -c.col()));
      }
      return new Shape(flipped).normalize();
    }

    List<Shape> allOrientations() {
      Set<Set<Cell>> seen = new HashSet<>();
      List<Shape> result = new ArrayList<>();

      Shape current = this.normalize();
      for (int flip = 0; flip < 2; flip++) {
        for (int rot = 0; rot < 4; rot++) {
          if (seen.add(current.cells())) {
            result.add(current);
          }
          current = current.rotate90();
        }
        current = current.flipHorizontal();
      }

      return result;
    }

    int width() {
      return cells.stream().mapToInt(Cell::col).max().orElse(0) + 1;
    }

    int height() {
      return cells.stream().mapToInt(Cell::row).max().orElse(0) + 1;
    }

    Shape translate(int dRow, int dCol) {
      Set<Cell> translated = new HashSet<>();
      for (Cell c : cells) {
        translated.add(new Cell(c.row() + dRow, c.col() + dCol));
      }
      return new Shape(translated);
    }
  }

  record Region(int width, int height, List<Integer> presentCounts) {

    boolean canFitAllPresents(List<Shape> shapeDefinitions) {
      int totalCellsNeeded = 0;
      for (int i = 0; i < presentCounts.size(); i++) {
        totalCellsNeeded += presentCounts.get(i) * shapeDefinitions.get(i).cellCount();
      }

      int area = width * height;
      if (totalCellsNeeded > area) {
        return false;
      }

      // With sufficient spare space, greedy packing always succeeds
      // For tight fits, we need to do actual backtracking
      int spareSpace = area - totalCellsNeeded;
      if (spareSpace >= 100) {
        return canPackGreedy(shapeDefinitions);
      }

      return canPackBacktrack(shapeDefinitions);
    }

    private boolean canPackGreedy(List<Shape> shapeDefinitions) {
      Set<Cell> occupied = new HashSet<>();

      for (int shapeIdx = 0; shapeIdx < presentCounts.size(); shapeIdx++) {
        int count = presentCounts.get(shapeIdx);
        Shape shape = shapeDefinitions.get(shapeIdx);

        for (int i = 0; i < count; i++) {
          boolean placed = false;

          for (Shape oriented : shape.allOrientations()) {
            if (placed) break;

            for (int row = 0; row <= height - oriented.height(); row++) {
              if (placed) break;

              for (int col = 0; col <= width - oriented.width(); col++) {
                Shape translated = oriented.translate(row, col);
                if (canPlace(translated.cells(), occupied)) {
                  occupied.addAll(translated.cells());
                  placed = true;
                  break;
                }
              }
            }
          }

          if (!placed) {
            return false;
          }
        }
      }

      return true;
    }

    private boolean canPackBacktrack(List<Shape> shapeDefinitions) {
      List<List<Set<Cell>>> allPlacements = new ArrayList<>();

      for (int shapeIdx = 0; shapeIdx < presentCounts.size(); shapeIdx++) {
        int count = presentCounts.get(shapeIdx);
        Shape shape = shapeDefinitions.get(shapeIdx);

        List<Set<Cell>> placements = new ArrayList<>();
        for (Shape oriented : shape.allOrientations()) {
          for (int row = 0; row <= height - oriented.height(); row++) {
            for (int col = 0; col <= width - oriented.width(); col++) {
              Shape placed = oriented.translate(row, col);
              if (fitsInBounds(placed)) {
                placements.add(placed.cells());
              }
            }
          }
        }

        for (int i = 0; i < count; i++) {
          allPlacements.add(placements);
        }
      }

      if (allPlacements.isEmpty()) {
        return true;
      }

      // Sort by fewest placements first (most constrained)
      allPlacements.sort((a, b) -> Integer.compare(a.size(), b.size()));

      return backtrack(0, new HashSet<>(), allPlacements);
    }

    private boolean fitsInBounds(Shape shape) {
      for (Cell c : shape.cells()) {
        if (c.row() < 0 || c.row() >= height || c.col() < 0 || c.col() >= width) {
          return false;
        }
      }
      return true;
    }

    private boolean backtrack(int index, Set<Cell> occupied, List<List<Set<Cell>>> allPlacements) {
      if (index == allPlacements.size()) {
        return true;
      }

      for (Set<Cell> placement : allPlacements.get(index)) {
        if (canPlace(placement, occupied)) {
          occupied.addAll(placement);
          if (backtrack(index + 1, occupied, allPlacements)) {
            return true;
          }
          occupied.removeAll(placement);
        }
      }

      return false;
    }

    private boolean canPlace(Set<Cell> placement, Set<Cell> occupied) {
      for (Cell c : placement) {
        if (occupied.contains(c)) {
          return false;
        }
      }
      return true;
    }
  }

  record PuzzleData(List<Shape> shapes, List<Region> regions) {

    static PuzzleData parse(String input) {
      List<Shape> shapes = new ArrayList<>();
      List<Region> regions = new ArrayList<>();

      String[] sections = input.split("\n\n");

      for (String section : sections) {
        String[] lines = section.trim().split("\n");
        if (lines.length == 0 || lines[0].isBlank()) continue;

        String firstLine = lines[0].trim();

        // Check if it's a shape definition (starts with "N:")
        if (firstLine.matches("\\d+:.*")) {
          List<String> shapeLines = new ArrayList<>();
          for (int i = 1; i < lines.length; i++) {
            if (!lines[i].isBlank()) {
              shapeLines.add(lines[i]);
            }
          }
          if (!shapeLines.isEmpty()) {
            shapes.add(Shape.fromLines(shapeLines));
          }
        }
        // Check if it's a region definition (WxH: ...)
        else if (firstLine.matches("\\d+x\\d+:.*")) {
          for (String line : lines) {
            line = line.trim();
            if (line.matches("\\d+x\\d+:.*")) {
              regions.add(parseRegion(line));
            }
          }
        }
      }

      // Also handle case where regions are on separate lines without blank line separation
      for (String line : input.split("\n")) {
        line = line.trim();
        if (line.matches("\\d+x\\d+:.*") && !containsRegion(regions, line)) {
          regions.add(parseRegion(line));
        }
      }

      return new PuzzleData(shapes, regions);
    }

    private static boolean containsRegion(List<Region> regions, String line) {
      Region parsed = parseRegion(line);
      return regions.stream()
          .anyMatch(
              r ->
                  r.width() == parsed.width()
                      && r.height() == parsed.height()
                      && r.presentCounts().equals(parsed.presentCounts()));
    }

    private static Region parseRegion(String line) {
      String[] parts = line.split(":");
      String[] dims = parts[0].trim().split("x");
      int width = Integer.parseInt(dims[0]);
      int height = Integer.parseInt(dims[1]);

      List<Integer> counts = new ArrayList<>();
      if (parts.length > 1 && !parts[1].trim().isEmpty()) {
        counts = Arrays.stream(parts[1].trim().split("\\s+")).map(Integer::parseInt).toList();
      }

      return new Region(width, height, counts);
    }

    long countFittingRegions() {
      return regions.stream().filter(r -> r.canFitAllPresents(shapes)).count();
    }
  }
}
