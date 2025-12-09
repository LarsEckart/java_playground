package lars.advent2025;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

class Day09 {

  static final String EXAMPLE =
      """
      7,1
      11,1
      11,7
      9,7
      9,5
      2,5
      2,3
      7,3
      """;

  @Nested
  class PartOne {

    @Test
    void parseTiles() {
      TileGrid grid = TileGrid.parse(EXAMPLE);

      assertThat(grid.redTiles()).hasSize(8);
      assertThat(grid.redTiles()).contains(new Tile(7, 1), new Tile(11, 1), new Tile(2, 5));
    }

    @Test
    void rectangleAreaBetweenTiles() {
      Tile a = new Tile(2, 5);
      Tile b = new Tile(9, 7);

      assertThat(a.rectangleAreaWith(b)).isEqualTo(24);
    }

    @Test
    void rectangleAreaBetweenTiles_7_1_and_11_7() {
      Tile a = new Tile(7, 1);
      Tile b = new Tile(11, 7);

      assertThat(a.rectangleAreaWith(b)).isEqualTo(35);
    }

    @Test
    void rectangleAreaBetweenTiles_7_3_and_2_3() {
      Tile a = new Tile(7, 3);
      Tile b = new Tile(2, 3);

      assertThat(a.rectangleAreaWith(b)).isEqualTo(6);
    }

    @Test
    void rectangleAreaBetweenTiles_2_5_and_11_1() {
      Tile a = new Tile(2, 5);
      Tile b = new Tile(11, 1);

      assertThat(a.rectangleAreaWith(b)).isEqualTo(50);
    }

    @Test
    void largestRectangleInExample() {
      TileGrid grid = TileGrid.parse(EXAMPLE);

      long result = grid.largestRectangleArea();

      assertThat(result).isEqualTo(50);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = ".*")
    @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = ".*")
    void puzzleInput() throws Exception {
      Path inputPath = AdventInputs.ensureDayInput(2025, 9);
      String input = Files.readString(inputPath);

      TileGrid grid = TileGrid.parse(input);
      long result = grid.largestRectangleArea();
      System.out.println("Day 9 Part 1: " + result);

      assertThat(result).isGreaterThan(0);
    }
  }

  // Domain objects

  record Tile(int x, int y) {

    static Tile parse(String line) {
      String[] parts = line.split(",");
      return new Tile(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    /**
     * Calculates the area of a rectangle with this tile and another as opposite corners. The
     * rectangle includes both corner tiles.
     */
    long rectangleAreaWith(Tile other) {
      // Width and height include both endpoints, so we add 1
      long width = Math.abs((long) x - other.x) + 1;
      long height = Math.abs((long) y - other.y) + 1;
      return width * height;
    }
  }

  record TileGrid(List<Tile> redTiles) {

    static TileGrid parse(String input) {
      List<Tile> tiles = input.lines().filter(line -> !line.isBlank()).map(Tile::parse).toList();
      return new TileGrid(tiles);
    }

    /** Finds the largest rectangle area using any two red tiles as opposite corners. */
    long largestRectangleArea() {
      long maxArea = 0;
      for (int i = 0; i < redTiles.size(); i++) {
        for (int j = i + 1; j < redTiles.size(); j++) {
          long area = redTiles.get(i).rectangleAreaWith(redTiles.get(j));
          maxArea = Math.max(maxArea, area);
        }
      }
      return maxArea;
    }
  }
}
