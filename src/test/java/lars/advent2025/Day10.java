package lars.advent2025;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
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
    void parseDiagram() {
      String line = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}";
      Diagram diagram = Diagram.parse(line);

      assertThat(diagram.numLights()).isEqualTo(4);
      assertThat(diagram.target()).isEqualTo(List.of(false, true, true, false)); // .##.
      assertThat(diagram.lights()).isEqualTo(List.of(false, false, false, false)); // all off
      assertThat(diagram.isConfigured()).isFalse();
    }

    @Test
    void parseButtons() {
      String line = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}";
      List<Button> buttons = Button.parseAll(line);

      assertThat(buttons).hasSize(6);
      assertThat(buttons.get(0).toggleIndices()).containsExactly(3);
      assertThat(buttons.get(1).toggleIndices()).containsExactly(1, 3);
    }

    @Test
    void parseSecondDiagram() {
      String line = "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}";
      Diagram diagram = Diagram.parse(line);

      assertThat(diagram.numLights()).isEqualTo(5);
      assertThat(diagram.target()).isEqualTo(List.of(false, false, false, true, false)); // ...#.
    }

    @Test
    void pressButtonTogglesLights() {
      Diagram diagram = Diagram.parse("[.##.] (1,3) {1}");
      Button button = new Button(List.of(1, 3)); // toggles lights 1 and 3

      Diagram after = diagram.pressButton(button);

      assertThat(after.lights()).isEqualTo(List.of(false, true, false, true));
      assertThat(diagram.lights())
          .isEqualTo(List.of(false, false, false, false)); // original unchanged
    }

    @Test
    void pressButtonTwiceReturnsToOriginal() {
      Diagram diagram = Diagram.parse("[.##.] (1,3) {1}");
      Button button = new Button(List.of(1, 3));

      Diagram after = diagram.pressButton(button).pressButton(button);

      assertThat(after.lights()).isEqualTo(diagram.lights());
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

  record Button(List<Integer> toggleIndices) {

    private static final Pattern PATTERN = Pattern.compile("\\(([0-9,]+)\\)");

    static List<Button> parseAll(String line) {
      List<Button> buttons = new ArrayList<>();
      Matcher matcher = PATTERN.matcher(line);
      while (matcher.find()) {
        List<Integer> indices =
            Arrays.stream(matcher.group(1).split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
        buttons.add(new Button(indices));
      }
      return buttons;
    }
  }

  record Diagram(List<Boolean> target, List<Boolean> lights) {

    private static final Pattern PATTERN = Pattern.compile("\\[([.#]+)]");

    static Diagram parse(String line) {
      Matcher matcher = PATTERN.matcher(line);
      if (!matcher.find()) {
        throw new IllegalArgumentException("No diagram found in: " + line);
      }
      String pattern = matcher.group(1);
      List<Boolean> target = new ArrayList<>();
      List<Boolean> lights = new ArrayList<>();
      for (int i = 0; i < pattern.length(); i++) {
        target.add(pattern.charAt(i) == '#');
        lights.add(false);
      }
      return new Diagram(target, lights);
    }

    Diagram pressButton(Button button) {
      List<Boolean> newLights = new ArrayList<>(lights);
      for (int index : button.toggleIndices()) {
        newLights.set(index, !newLights.get(index));
      }
      return new Diagram(target, newLights);
    }

    boolean isConfigured() {
      return lights.equals(target);
    }

    int numLights() {
      return lights.size();
    }
  }

  record Machine(Diagram diagram, List<Button> buttons) {

    static Machine parse(String line) {
      return new Machine(Diagram.parse(line), Button.parseAll(line));
    }

    /**
     * Finds the minimum number of button presses to reach the target state. Uses BFS over button
     * combinations since each button press toggles (XOR). Key insight: pressing a button twice is
     * the same as not pressing it, so we only need to consider each button being pressed 0 or 1
     * time.
     */
    int findMinimumPresses() {
      Map<List<Boolean>, Integer> visited = new HashMap<>();
      Queue<Diagram> queue = new LinkedList<>();

      queue.add(diagram);
      visited.put(diagram.lights(), 0);

      while (!queue.isEmpty()) {
        Diagram current = queue.poll();
        int presses = visited.get(current.lights());

        if (current.isConfigured()) {
          return presses;
        }

        for (Button button : buttons) {
          Diagram next = current.pressButton(button);
          if (!visited.containsKey(next.lights())) {
            visited.put(next.lights(), presses + 1);
            queue.add(next);
          }
        }
      }

      throw new IllegalStateException("No solution found for target: " + diagram.target());
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
