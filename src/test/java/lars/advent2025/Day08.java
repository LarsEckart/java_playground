package lars.advent2025;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

class Day08 {

  static final String EXAMPLE =
      """
      162,817,812
      57,618,57
      906,360,560
      592,479,940
      352,342,300
      466,668,158
      542,29,236
      431,825,988
      739,650,466
      52,470,668
      216,146,977
      819,987,18
      117,168,530
      805,96,715
      346,949,466
      970,615,88
      941,993,340
      862,61,35
      984,92,344
      425,690,689
      """;

  @Nested
  class PartOne {

    @Test
    void parseJunctionBoxes() {
      Playground playground = Playground.parse(EXAMPLE);

      assertThat(playground.boxes()).hasSize(20);
      assertThat(playground.boxes().getFirst()).isEqualTo(new JunctionBox(162, 817, 812));
    }

    @Test
    void distanceBetweenBoxes() {
      JunctionBox a = new JunctionBox(162, 817, 812);
      JunctionBox b = new JunctionBox(425, 690, 689);

      double distance = a.distanceTo(b);

      assertThat(distance).isCloseTo(316.90, offset(0.01));
    }

    @Test
    void closestPairInExample() {
      Playground playground = Playground.parse(EXAMPLE);

      BoxPair closest = playground.sortedPairs().getFirst();

      // First closest: 162,817,812 and 425,690,689
      assertThat(closest.a()).isEqualTo(new JunctionBox(162, 817, 812));
      assertThat(closest.b()).isEqualTo(new JunctionBox(425, 690, 689));
    }

    @Test
    void afterTenConnections() {
      Playground playground = Playground.parse(EXAMPLE);

      long result = playground.connectAndMultiplyLargest(10, 3);

      // After 10 connections: circuits of size 5, 4, 2, 2, and seven 1s
      // 5 * 4 * 2 = 40
      assertThat(result).isEqualTo(40);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = ".*")
    @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = ".*")
    void puzzleInput() throws Exception {
      Path inputPath = AdventInputs.ensureDayInput(2025, 8);
      String input = Files.readString(inputPath);

      Playground playground = Playground.parse(input);
      long result = playground.connectAndMultiplyLargest(1000, 3);
      System.out.println("Day 8 Part 1: " + result);

      assertThat(result).isEqualTo(57564);
    }
  }

  @Nested
  class PartTwo {

    @Test
    void lastConnectionToUnify() {
      Playground playground = Playground.parse(EXAMPLE);

      BoxPair lastPair = playground.connectUntilUnified();

      // Last connection: 216,146,977 and 117,168,530
      assertThat(lastPair.a().x() * lastPair.b().x()).isEqualTo(25272);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = ".*")
    @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = ".*")
    void puzzleInput() throws Exception {
      Path inputPath = AdventInputs.ensureDayInput(2025, 8);
      String input = Files.readString(inputPath);

      Playground playground = Playground.parse(input);
      BoxPair lastPair = playground.connectUntilUnified();
      long result = (long) lastPair.a().x() * lastPair.b().x();
      System.out.println("Day 8 Part 2: " + result);

      assertThat(result).isEqualTo(133296744L);
    }
  }

  // Domain objects

  record JunctionBox(int x, int y, int z) {

    static JunctionBox parse(String line) {
      String[] parts = line.split(",");
      return new JunctionBox(
          Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    double distanceTo(JunctionBox other) {
      long dx = (long) x - other.x;
      long dy = (long) y - other.y;
      long dz = (long) z - other.z;
      return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    long distanceSquaredTo(JunctionBox other) {
      long dx = (long) x - other.x;
      long dy = (long) y - other.y;
      long dz = (long) z - other.z;
      return dx * dx + dy * dy + dz * dz;
    }
  }

  record BoxPair(JunctionBox a, JunctionBox b, long distanceSquared) {

    static BoxPair of(JunctionBox a, JunctionBox b) {
      return new BoxPair(a, b, a.distanceSquaredTo(b));
    }
  }

  static class UnionFind {
    private final int[] parent;
    private final int[] size;

    UnionFind(int n) {
      parent = new int[n];
      size = new int[n];
      for (int i = 0; i < n; i++) {
        parent[i] = i;
        size[i] = 1;
      }
    }

    int find(int x) {
      if (parent[x] != x) {
        parent[x] = find(parent[x]); // path compression
      }
      return parent[x];
    }

    boolean union(int x, int y) {
      int rootX = find(x);
      int rootY = find(y);
      if (rootX == rootY) {
        return false; // already in same circuit
      }
      // union by size
      if (size[rootX] < size[rootY]) {
        parent[rootX] = rootY;
        size[rootY] += size[rootX];
      } else {
        parent[rootY] = rootX;
        size[rootX] += size[rootY];
      }
      return true;
    }

    List<Integer> circuitSizes() {
      List<Integer> sizes = new ArrayList<>();
      for (int i = 0; i < parent.length; i++) {
        if (parent[i] == i) {
          sizes.add(size[i]);
        }
      }
      sizes.sort(Comparator.reverseOrder());
      return sizes;
    }

    int circuitCount() {
      int count = 0;
      for (int i = 0; i < parent.length; i++) {
        if (parent[i] == i) {
          count++;
        }
      }
      return count;
    }
  }

  record Playground(List<JunctionBox> boxes) {

    static Playground parse(String input) {
      List<JunctionBox> boxes =
          input.lines().filter(line -> !line.isBlank()).map(JunctionBox::parse).toList();
      return new Playground(boxes);
    }

    List<BoxPair> sortedPairs() {
      List<BoxPair> pairs = new ArrayList<>();
      for (int i = 0; i < boxes.size(); i++) {
        for (int j = i + 1; j < boxes.size(); j++) {
          pairs.add(BoxPair.of(boxes.get(i), boxes.get(j)));
        }
      }
      pairs.sort(Comparator.comparingLong(BoxPair::distanceSquared));
      return pairs;
    }

    long connectAndMultiplyLargest(int connections, int topN) {
      UnionFind uf = new UnionFind(boxes.size());
      List<BoxPair> pairs = sortedPairs();

      int connectionsMade = 0;
      for (BoxPair pair : pairs) {
        if (connectionsMade >= connections) {
          break;
        }
        int indexA = boxes.indexOf(pair.a());
        int indexB = boxes.indexOf(pair.b());
        uf.union(indexA, indexB);
        connectionsMade++;
      }

      List<Integer> sizes = uf.circuitSizes();
      long result = 1;
      for (int i = 0; i < Math.min(topN, sizes.size()); i++) {
        result *= sizes.get(i);
      }
      return result;
    }

    BoxPair connectUntilUnified() {
      UnionFind uf = new UnionFind(boxes.size());
      List<BoxPair> pairs = sortedPairs();

      BoxPair lastConnection = null;
      for (BoxPair pair : pairs) {
        int indexA = boxes.indexOf(pair.a());
        int indexB = boxes.indexOf(pair.b());
        if (uf.union(indexA, indexB)) {
          lastConnection = pair;
          if (uf.circuitCount() == 1) {
            break;
          }
        }
      }
      return lastConnection;
    }
  }
}
