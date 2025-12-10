package lars.advent2025;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lars.advent.PuzzleInput;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

class Day10 {

  static final String EXAMPLE =
      """
      [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
      [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
      [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
      """;

  @Nested
  class PartOne {

    @Test
    void parseMachine() {
      String line = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}";
      Machine machine = Machine.parse(line);

      assertThat(machine.numLights()).isEqualTo(4);
      assertThat(machine.targetState()).isEqualTo(0b0110); // .##. -> bits 1,2 are on
      assertThat(machine.buttons()).hasSize(6);
      assertThat(machine.buttons().get(0).toggleMask()).isEqualTo(0b1000); // toggles light 3
      assertThat(machine.buttons().get(1).toggleMask()).isEqualTo(0b1010); // toggles lights 1,3
    }

    @Test
    void parseSecondMachine() {
      String line = "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}";
      Machine machine = Machine.parse(line);

      assertThat(machine.numLights()).isEqualTo(5);
      assertThat(machine.targetState()).isEqualTo(0b01000); // ...#. -> bit 3 is on
    }

    @Test
    void toggleLights() {
      // Start with all off (0), toggle lights 1 and 3
      int state = 0b0000;
      Button button = new Button(0b1010); // toggles bits 1 and 3

      int newState = button.toggle(state);

      assertThat(newState).isEqualTo(0b1010);
    }

    @Test
    void toggleTwiceReturnsToOriginal() {
      int state = 0b0000;
      Button button = new Button(0b1010);

      int newState = button.toggle(button.toggle(state));

      assertThat(newState).isEqualTo(state);
    }

    @Test
    void minPressesForFirstMachine() {
      String line = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}";
      Machine machine = Machine.parse(line);

      int minPresses = machine.findMinimumPresses();

      assertThat(minPresses).isEqualTo(2);
    }

    @Test
    void minPressesForSecondMachine() {
      String line = "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}";
      Machine machine = Machine.parse(line);

      int minPresses = machine.findMinimumPresses();

      assertThat(minPresses).isEqualTo(3);
    }

    @Test
    void minPressesForThirdMachine() {
      String line = "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}";
      Machine machine = Machine.parse(line);

      int minPresses = machine.findMinimumPresses();

      assertThat(minPresses).isEqualTo(2);
    }

    @Test
    void totalMinPressesForExample() {
      int total = Factory.parse(EXAMPLE).totalMinimumPresses();

      assertThat(total).isEqualTo(7);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = ".*")
    @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = ".*")
    void puzzleInput() throws Exception {
      Path inputPath = PuzzleInput.forDate(2025, 10);
      String input = Files.readString(inputPath);

      int result = Factory.parse(input).totalMinimumPresses();
      System.out.println("Day 10 Part 1: " + result);

      assertThat(result).isEqualTo(444);
    }
  }

  // Domain objects

  record Button(int toggleMask) {

    int toggle(int state) {
      return state ^ toggleMask;
    }
  }

  record Machine(int numLights, int targetState, List<Button> buttons) {

    private static final Pattern DIAGRAM_PATTERN = Pattern.compile("\\[([.#]+)]");
    private static final Pattern BUTTON_PATTERN = Pattern.compile("\\(([0-9,]+)\\)");

    static Machine parse(String line) {
      // Parse indicator light diagram [.##.]
      Matcher diagramMatcher = DIAGRAM_PATTERN.matcher(line);
      if (!diagramMatcher.find()) {
        throw new IllegalArgumentException("No diagram found in: " + line);
      }
      String diagram = diagramMatcher.group(1);
      int numLights = diagram.length();
      int targetState = 0;
      for (int i = 0; i < diagram.length(); i++) {
        if (diagram.charAt(i) == '#') {
          targetState |= (1 << i);
        }
      }

      // Parse button wiring schematics (0,1,2)
      List<Button> buttons = new ArrayList<>();
      Matcher buttonMatcher = BUTTON_PATTERN.matcher(line);
      while (buttonMatcher.find()) {
        String buttonSpec = buttonMatcher.group(1);
        int mask = 0;
        for (String index : buttonSpec.split(",")) {
          mask |= (1 << Integer.parseInt(index.trim()));
        }
        buttons.add(new Button(mask));
      }

      return new Machine(numLights, targetState, buttons);
    }

    /**
     * Finds the minimum number of button presses to reach the target state. Uses BFS over button
     * combinations since each button press toggles (XOR). Key insight: pressing a button twice is
     * the same as not pressing it, so we only need to consider each button being pressed 0 or 1
     * time.
     */
    int findMinimumPresses() {
      // BFS: state -> minimum presses to reach it
      Map<Integer, Integer> visited = new HashMap<>();
      Queue<int[]> queue = new LinkedList<>();

      // Start with all lights off (state 0), 0 presses
      queue.add(new int[] {0, 0});
      visited.put(0, 0);

      while (!queue.isEmpty()) {
        int[] current = queue.poll();
        int state = current[0];
        int presses = current[1];

        if (state == targetState) {
          return presses;
        }

        // Try pressing each button
        for (Button button : buttons) {
          int newState = button.toggle(state);
          if (!visited.containsKey(newState)) {
            visited.put(newState, presses + 1);
            queue.add(new int[] {newState, presses + 1});
          }
        }
      }

      // No solution found (shouldn't happen for valid input)
      throw new IllegalStateException("No solution found for target state: " + targetState);
    }
  }

  record Factory(List<Machine> machines) {

    static Factory parse(String input) {
      List<Machine> machines =
          input.lines().filter(line -> !line.isBlank()).map(Machine::parse).toList();
      return new Factory(machines);
    }

    int totalMinimumPresses() {
      return machines.stream().mapToInt(Machine::findMinimumPresses).sum();
    }
  }
}
