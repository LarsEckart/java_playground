package lars.advent2025;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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

      assertThat(result).isEqualTo(500);
    }
  }

  @Nested
  class PartTwo {

    static final String EXAMPLE2 =
        """
        svr: aaa bbb
        aaa: fft
        fft: ccc
        bbb: tty
        tty: ccc
        ccc: ddd eee
        ddd: hub
        hub: fff
        eee: dac
        dac: fff
        fff: ggg hhh
        ggg: out
        hhh: out
        """;

    @Test
    void countPathsVisitingBothDacAndFft() {
      DeviceNetwork network = DeviceNetwork.parse(EXAMPLE2);

      long pathCount = network.countPathsVisiting("svr", "out", Set.of("dac", "fft"));

      assertThat(pathCount).isEqualTo(2);
    }

    @Test
    void totalPathsFromSvrToOut() {
      DeviceNetwork network = DeviceNetwork.parse(EXAMPLE2);

      long pathCount = network.countPaths("svr", "out");

      assertThat(pathCount).isEqualTo(8);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = ".*")
    @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = ".*")
    void puzzleInput() throws Exception {
      Path inputPath = PuzzleInput.forDate(2025, 11);
      String input = Files.readString(inputPath);

      DeviceNetwork network = DeviceNetwork.parse(input);
      long result = network.countPathsVisiting("svr", "out", Set.of("dac", "fft"));
      System.out.println("Day 11 Part 2: " + result);

      assertThat(result).isGreaterThan(0);
    }
  }

  // Domain objects

  record DeviceNetwork(Map<String, List<String>> adjacency) {

    static DeviceNetwork parse(String input) {
      Map<String, List<String>> adjacency =
          input
              .lines()
              .filter(line -> !line.isBlank())
              .collect(
                  Collectors.toMap(
                      line -> line.split(":")[0].trim(),
                      line -> {
                        String[] parts = line.split(":");
                        return parts.length > 1
                            ? Arrays.stream(parts[1].trim().split("\\s+"))
                                .filter(s -> !s.isBlank())
                                .toList()
                            : List.of();
                      }));

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

    /**
     * Counts paths from start to end that visit ALL required nodes. Uses memoization with state
     * tracking which required nodes have been visited (as a bitmask).
     */
    long countPathsVisiting(String start, String end, Set<String> required) {
      List<String> requiredList = required.stream().sorted().toList();
      Map<MemoKey, Long> memo = new HashMap<>();
      int initialMask = computeMask(start, requiredList);
      return countPathsVisitingMemoized(start, end, requiredList, initialMask, memo);
    }

    private long countPathsVisitingMemoized(
        String current,
        String end,
        List<String> required,
        int visitedMask,
        Map<MemoKey, Long> memo) {

      int allVisited = (1 << required.size()) - 1;

      if (current.equals(end)) {
        return visitedMask == allVisited ? 1 : 0;
      }

      MemoKey key = new MemoKey(current, visitedMask);
      if (memo.containsKey(key)) {
        return memo.get(key);
      }

      long totalPaths = 0;
      for (String next : outputs(current)) {
        int newMask = visitedMask | computeMask(next, required);
        totalPaths += countPathsVisitingMemoized(next, end, required, newMask, memo);
      }

      memo.put(key, totalPaths);
      return totalPaths;
    }

    private int computeMask(String node, List<String> required) {
      int mask = 0;
      for (int i = 0; i < required.size(); i++) {
        if (required.get(i).equals(node)) {
          mask |= (1 << i);
        }
      }
      return mask;
    }

    record MemoKey(String node, int visitedMask) {}
  }
}
