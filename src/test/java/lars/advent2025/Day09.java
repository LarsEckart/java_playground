package lars.advent2025;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

      assertThat(result).isEqualTo(4750297200L);
    }
  }

  @Nested
  class PartTwo {

    @Test
    void buildGreenTilesOnEdges() {
      TileGrid grid = TileGrid.parse(EXAMPLE);
      Set<Tile> colored = grid.buildColoredTiles();

      // Check some edge green tiles
      assertThat(colored)
          .contains(new Tile(8, 1), new Tile(9, 1), new Tile(10, 1)); // between 7,1 and 11,1
      assertThat(colored).contains(new Tile(11, 2), new Tile(11, 3)); // between 11,1 and 11,7
    }

    @Test
    void buildColoredTilesIncludesInterior() {
      TileGrid grid = TileGrid.parse(EXAMPLE);
      Set<Tile> colored = grid.buildColoredTiles();

      // Interior tiles should be green
      assertThat(colored).contains(new Tile(8, 2), new Tile(9, 3), new Tile(5, 4));
      // Exterior tiles should not be colored
      assertThat(colored).doesNotContain(new Tile(0, 0), new Tile(1, 1), new Tile(12, 8));
    }

    @Test
    void rectangleFitsInColoredTiles() {
      TileGrid grid = TileGrid.parse(EXAMPLE);
      Set<Tile> colored = grid.buildColoredTiles();

      // Rectangle 7,3 to 11,1 (area 15) should fit
      assertThat(grid.rectangleFitsInColored(new Tile(7, 3), new Tile(11, 1), colored)).isTrue();

      // Rectangle 9,7 to 9,5 (area 3) should fit
      assertThat(grid.rectangleFitsInColored(new Tile(9, 7), new Tile(9, 5), colored)).isTrue();

      // Rectangle 2,5 to 11,1 (area 50) should NOT fit (extends outside)
      assertThat(grid.rectangleFitsInColored(new Tile(2, 5), new Tile(11, 1), colored)).isFalse();
    }

    @Test
    void largestColoredRectangleInExample() {
      TileGrid grid = TileGrid.parse(EXAMPLE);

      long result = grid.largestColoredRectangleArea();

      assertThat(result).isEqualTo(24);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = ".*")
    @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = ".*")
    void puzzleInput() throws Exception {
      Path inputPath = AdventInputs.ensureDayInput(2025, 9);
      String input = Files.readString(inputPath);

      TileGrid grid = TileGrid.parse(input);
      long result = grid.largestColoredRectangleArea();
      System.out.println("Day 9 Part 2: " + result);

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

  record Edge(Tile from, Tile to) {

    boolean isVertical() {
      return from.x() == to.x();
    }

    boolean isHorizontal() {
      return from.y() == to.y();
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

    /** Builds list of polygon edges (axis-aligned segments between consecutive red tiles). */
    List<Edge> buildEdges() {
      List<Edge> edges = new ArrayList<>();
      for (int i = 0; i < redTiles.size(); i++) {
        Tile from = redTiles.get(i);
        Tile to = redTiles.get((i + 1) % redTiles.size());
        edges.add(new Edge(from, to));
      }
      return edges;
    }

    /**
     * Finds largest rectangle with red corners where all tiles are colored (red or green). Uses
     * geometric approach: rectangle fits if no edge passes through its strict interior.
     */
    long largestColoredRectangleArea() {
      List<Edge> edges = buildEdges();
      long maxArea = 0;

      for (int i = 0; i < redTiles.size(); i++) {
        for (int j = i + 1; j < redTiles.size(); j++) {
          Tile a = redTiles.get(i);
          Tile b = redTiles.get(j);
          if (rectangleFitsGeometric(a, b, edges)) {
            long area = a.rectangleAreaWith(b);
            maxArea = Math.max(maxArea, area);
          }
        }
      }
      return maxArea;
    }

    /**
     * Rectangle fits if no polygon edge passes through the rectangle's strict interior. An edge can
     * touch the boundary but not cross through.
     */
    boolean rectangleFitsGeometric(Tile a, Tile b, List<Edge> edges) {
      int minX = Math.min(a.x(), b.x());
      int maxX = Math.max(a.x(), b.x());
      int minY = Math.min(a.y(), b.y());
      int maxY = Math.max(a.y(), b.y());

      for (Edge edge : edges) {
        if (edgeCrossesRectangleInterior(edge, minX, maxX, minY, maxY)) {
          return false;
        }
      }
      return true;
    }

    /**
     * Checks if an axis-aligned edge crosses through the strict interior of the rectangle. Edge can
     * be on boundary, just not cut through the inside.
     */
    private boolean edgeCrossesRectangleInterior(
        Edge edge, int minX, int maxX, int minY, int maxY) {
      int ex1 = edge.from().x();
      int ey1 = edge.from().y();
      int ex2 = edge.to().x();
      int ey2 = edge.to().y();

      if (edge.isVertical()) {
        // Vertical edge
        int edgeX = ex1;
        int edgeMinY = Math.min(ey1, ey2);
        int edgeMaxY = Math.max(ey1, ey2);

        // Edge is in strict interior if x is strictly between minX and maxX
        // and the edge's y-range overlaps with rectangle's y-range
        if (edgeX > minX && edgeX < maxX) {
          // Check if edge overlaps with rectangle in y direction
          return edgeMaxY > minY && edgeMinY < maxY;
        }
      } else if (edge.isHorizontal()) {
        // Horizontal edge
        int edgeY = ey1;
        int edgeMinX = Math.min(ex1, ex2);
        int edgeMaxX = Math.max(ex1, ex2);

        // Edge is in strict interior if y is strictly between minY and maxY
        // and the edge's x-range overlaps with rectangle's x-range
        if (edgeY > minY && edgeY < maxY) {
          // Check if edge overlaps with rectangle in x direction
          return edgeMaxX > minX && edgeMinX < maxX;
        }
      }
      return false;
    }

    // --- Methods for small grids (used in tests) ---

    /** Builds set of all colored tiles (red + green edges + green interior) - for small grids. */
    Set<Tile> buildColoredTiles() {
      Set<Tile> colored = new HashSet<>();

      // Add all red tiles and green tiles on edges between consecutive red tiles
      for (int i = 0; i < redTiles.size(); i++) {
        Tile from = redTiles.get(i);
        Tile to = redTiles.get((i + 1) % redTiles.size());
        addLineTiles(colored, from, to);
      }

      // Fill interior using ray casting (scanline approach)
      fillInterior(colored);

      return colored;
    }

    private void addLineTiles(Set<Tile> tiles, Tile from, Tile to) {
      int dx = Integer.compare(to.x(), from.x());
      int dy = Integer.compare(to.y(), from.y());
      int x = from.x();
      int y = from.y();
      while (x != to.x() || y != to.y()) {
        tiles.add(new Tile(x, y));
        x += dx;
        y += dy;
      }
      tiles.add(to);
    }

    private void fillInterior(Set<Tile> colored) {
      // Find bounding box
      int minX = redTiles.stream().mapToInt(Tile::x).min().orElse(0);
      int maxX = redTiles.stream().mapToInt(Tile::x).max().orElse(0);
      int minY = redTiles.stream().mapToInt(Tile::y).min().orElse(0);
      int maxY = redTiles.stream().mapToInt(Tile::y).max().orElse(0);

      // For each point inside bounding box, use ray casting to determine if inside
      for (int y = minY; y <= maxY; y++) {
        for (int x = minX; x <= maxX; x++) {
          if (!colored.contains(new Tile(x, y)) && isInsidePolygon(x, y)) {
            colored.add(new Tile(x, y));
          }
        }
      }
    }

    /**
     * Ray casting algorithm: count edge crossings from point to infinity. Odd count means inside.
     */
    private boolean isInsidePolygon(int px, int py) {
      int crossings = 0;
      for (int i = 0; i < redTiles.size(); i++) {
        Tile a = redTiles.get(i);
        Tile b = redTiles.get((i + 1) % redTiles.size());

        // Only consider vertical edges (same x coordinate)
        if (a.x() == b.x() && a.x() > px) {
          int minEdgeY = Math.min(a.y(), b.y());
          int maxEdgeY = Math.max(a.y(), b.y());
          // Check if horizontal ray at py crosses this vertical edge
          // Use half-open interval to avoid double-counting corners
          if (py >= minEdgeY && py < maxEdgeY) {
            crossings++;
          }
        }
      }
      return crossings % 2 == 1;
    }

    boolean rectangleFitsInColored(Tile a, Tile b, Set<Tile> colored) {
      int minX = Math.min(a.x(), b.x());
      int maxX = Math.max(a.x(), b.x());
      int minY = Math.min(a.y(), b.y());
      int maxY = Math.max(a.y(), b.y());

      for (int y = minY; y <= maxY; y++) {
        for (int x = minX; x <= maxX; x++) {
          if (!colored.contains(new Tile(x, y))) {
            return false;
          }
        }
      }
      return true;
    }
  }
}
