package lars.advent2025;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lars.advent.PuzzleInput;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

class Day11 {

  static final String EXAMPLE =
      """
      aaa: you hhh
      you: bbb ccc
      bbb: ddd eee
      ccc: ddd eee fff
      ddd: ggg
      eee: out
      fff: out
      ggg: out
      hhh: ccc fff iii
      iii: out
      """;

  @Nested
  class PartOne {

    @Test
    void parseDeviceNetwork() {
      DeviceNetwork network = DeviceNetwork.parse(EXAMPLE);

      assertThat(network.outputs("you")).containsExactly("bbb", "ccc");
      assertThat(network.outputs("bbb")).containsExactly("ddd", "eee");
      assertThat(network.outputs("out")).isEmpty();
    }

    @Test
    void countPathsInExample() {
      DeviceNetwork network = DeviceNetwork.parse(EXAMPLE);

      long pathCount = network.countPaths("you", "out");

      assertThat(pathCount).isEqualTo(5);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = ".*")
    @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = ".*")
    void puzzleInput() throws Exception {
      Path inputPath = PuzzleInput.forDate(2025, 11);
      String input = Files.readString(inputPath);

      DeviceNetwork network = DeviceNetwork.parse(input);
      long result = network.countPaths("you", "out");
      System.out.println("Day 11 Part 1: " + result);

      assertThat(result).isGreaterThan(0);
    }
  }

  // Domain objects

  record DeviceNetwork(Map<String, List<String>> adjacency) {

    static DeviceNetwork parse(String input) {
      Map<String, List<String>> adjacency = new HashMap<>();

      input
          .lines()
          .filter(line -> !line.isBlank())
          .forEach(
              line -> {
                String[] parts = line.split(":");
                String device = parts[0].trim();
                List<String> outputs =
                    parts.length > 1
                        ? Arrays.stream(parts[1].trim().split("\\s+"))
                            .filter(s -> !s.isBlank())
                            .toList()
                        : List.of();
                adjacency.put(device, outputs);
              });

      return new DeviceNetwork(adjacency);
    }

    List<String> outputs(String device) {
      return adjacency.getOrDefault(device, List.of());
    }

    /**
     * Counts all distinct paths from start to end using memoization. Since this is a DAG (data only
     * flows forward), we can use dynamic programming.
     */
    long countPaths(String start, String end) {
      Map<String, Long> memo = new HashMap<>();
      return countPathsMemoized(start, end, memo);
    }

    private long countPathsMemoized(String current, String end, Map<String, Long> memo) {
      if (current.equals(end)) {
        return 1;
      }

      if (memo.containsKey(current)) {
        return memo.get(current);
      }

      long totalPaths = 0;
      for (String next : outputs(current)) {
        totalPaths += countPathsMemoized(next, end, memo);
      }

      memo.put(current, totalPaths);
      return totalPaths;
    }
  }
}
