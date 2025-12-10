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
      assertThat(buttons.get(0).indices()).containsExactly(3);
      assertThat(buttons.get(1).indices()).containsExactly(1, 3);
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

  @Nested
  class PartTwo {

    @Test
    void parseJoltageRequirements() {
      String line = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}";
      JoltagePanel panel = JoltagePanel.parse(line);

      assertThat(panel.target()).isEqualTo(List.of(3, 5, 4, 7));
      assertThat(panel.counters()).isEqualTo(List.of(0, 0, 0, 0));
      assertThat(panel.isConfigured()).isFalse();
    }

    @Test
    void pressButtonIncrementsCounters() {
      JoltagePanel panel = JoltagePanel.parse("[.##.] (1,3) {3,5,4,7}");
      Button button = new Button(List.of(1, 3));

      JoltagePanel after = panel.pressButton(button);

      assertThat(after.counters()).isEqualTo(List.of(0, 1, 0, 1));
      assertThat(panel.counters()).isEqualTo(List.of(0, 0, 0, 0)); // original unchanged
    }

    @Test
    void minPressesForFirstMachineJoltage() {
      String line = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}";
      Machine machine = Machine.parse(line);

      int minPresses = machine.findMinimumJoltagePresses();

      assertThat(minPresses).isEqualTo(10);
    }

    @Test
    void minPressesForSecondMachineJoltage() {
      String line = "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}";
      Machine machine = Machine.parse(line);

      int minPresses = machine.findMinimumJoltagePresses();

      assertThat(minPresses).isEqualTo(12);
    }

    @Test
    void minPressesForThirdMachineJoltage() {
      String line = "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}";
      Machine machine = Machine.parse(line);

      int minPresses = machine.findMinimumJoltagePresses();

      assertThat(minPresses).isEqualTo(11);
    }

    @Test
    void totalMinPressesForExampleJoltage() {
      int total = Factory.parse(EXAMPLE).totalMinimumJoltagePresses();

      assertThat(total).isEqualTo(33);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = ".*")
    @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = ".*")
    void puzzleInput() throws Exception {
      Path inputPath = PuzzleInput.forDate(2025, 10);
      String input = Files.readString(inputPath);

      int result = Factory.parse(input).totalMinimumJoltagePresses();
      System.out.println("Day 10 Part 2: " + result);

      assertThat(result).isEqualTo(16513);
    }
  }

  // Domain objects

  record Button(List<Integer> indices) {

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

  record JoltagePanel(List<Integer> target, List<Integer> counters) {

    private static final Pattern PATTERN = Pattern.compile("\\{([0-9,]+)}");

    static JoltagePanel parse(String line) {
      Matcher matcher = PATTERN.matcher(line);
      if (!matcher.find()) {
        throw new IllegalArgumentException("No joltage requirements found in: " + line);
      }
      List<Integer> target =
          Arrays.stream(matcher.group(1).split(","))
              .map(String::trim)
              .map(Integer::parseInt)
              .toList();
      List<Integer> counters = new ArrayList<>();
      for (int i = 0; i < target.size(); i++) {
        counters.add(0);
      }
      return new JoltagePanel(target, counters);
    }

    JoltagePanel pressButton(Button button) {
      List<Integer> newCounters = new ArrayList<>(counters);
      for (int index : button.indices()) {
        newCounters.set(index, newCounters.get(index) + 1);
      }
      return new JoltagePanel(target, newCounters);
    }

    boolean isConfigured() {
      return counters.equals(target);
    }

    boolean isOvershot() {
      for (int i = 0; i < counters.size(); i++) {
        if (counters.get(i) > target.get(i)) {
          return true;
        }
      }
      return false;
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
      for (int index : button.indices()) {
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

  record Machine(Diagram diagram, JoltagePanel joltagePanel, List<Button> buttons) {

    static Machine parse(String line) {
      return new Machine(Diagram.parse(line), JoltagePanel.parse(line), Button.parseAll(line));
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

    /**
     * Finds minimum button presses to reach joltage targets. Uses Gaussian elimination to reduce
     * the system, then searches over free variables only.
     */
    int findMinimumJoltagePresses() {
      int numCounters = joltagePanel.target().size();
      int numButtons = buttons.size();

      // Build augmented matrix [A|b] using rationals (as double for simplicity)
      double[][] aug = new double[numCounters][numButtons + 1];
      for (int b = 0; b < numButtons; b++) {
        for (int index : buttons.get(b).indices()) {
          aug[index][b] = 1;
        }
      }
      for (int c = 0; c < numCounters; c++) {
        aug[c][numButtons] = joltagePanel.target().get(c);
      }

      // Gaussian elimination to row echelon form
      int[] pivotCol = new int[numCounters]; // which column is the pivot for each row
      Arrays.fill(pivotCol, -1);
      int pivotRow = 0;

      for (int col = 0; col < numButtons && pivotRow < numCounters; col++) {
        // Find pivot
        int maxRow = pivotRow;
        for (int row = pivotRow + 1; row < numCounters; row++) {
          if (Math.abs(aug[row][col]) > Math.abs(aug[maxRow][col])) {
            maxRow = row;
          }
        }

        if (Math.abs(aug[maxRow][col]) < 1e-10) {
          continue; // No pivot in this column
        }

        // Swap rows
        double[] temp = aug[pivotRow];
        aug[pivotRow] = aug[maxRow];
        aug[maxRow] = temp;

        pivotCol[pivotRow] = col;

        // Eliminate below
        for (int row = pivotRow + 1; row < numCounters; row++) {
          double factor = aug[row][col] / aug[pivotRow][col];
          for (int j = col; j <= numButtons; j++) {
            aug[row][j] -= factor * aug[pivotRow][j];
          }
        }
        pivotRow++;
      }

      // Back substitution to reduced row echelon form
      for (int row = pivotRow - 1; row >= 0; row--) {
        int col = pivotCol[row];
        if (col == -1) continue;

        // Normalize pivot row
        double pivot = aug[row][col];
        for (int j = 0; j <= numButtons; j++) {
          aug[row][j] /= pivot;
        }

        // Eliminate above
        for (int r = 0; r < row; r++) {
          double factor = aug[r][col];
          for (int j = 0; j <= numButtons; j++) {
            aug[r][j] -= factor * aug[row][j];
          }
        }
      }

      // Identify pivot and free variables
      boolean[] isPivot = new boolean[numButtons];
      List<Integer> freeVars = new ArrayList<>();
      for (int row = 0; row < pivotRow; row++) {
        if (pivotCol[row] >= 0) {
          isPivot[pivotCol[row]] = true;
        }
      }
      for (int b = 0; b < numButtons; b++) {
        if (!isPivot[b]) {
          freeVars.add(b);
        }
      }

      // Search over free variables
      int[] solution = new int[numButtons];
      int[] best = {Integer.MAX_VALUE};
      searchFreeVars(aug, pivotCol, pivotRow, freeVars, 0, solution, best, numButtons);

      if (best[0] == Integer.MAX_VALUE) {
        throw new IllegalStateException("No solution found");
      }
      return best[0];
    }

    private void searchFreeVars(
        double[][] aug,
        int[] pivotCol,
        int numPivotRows,
        List<Integer> freeVars,
        int freeIdx,
        int[] solution,
        int[] best,
        int numButtons) {

      if (freeIdx == freeVars.size()) {
        // All free variables assigned, compute pivot variables
        for (int row = numPivotRows - 1; row >= 0; row--) {
          int col = pivotCol[row];
          if (col == -1) continue;

          double val = aug[row][numButtons];
          for (int j = col + 1; j < numButtons; j++) {
            val -= aug[row][j] * solution[j];
          }

          // Check if integer and non-negative
          int intVal = (int) Math.round(val);
          if (Math.abs(val - intVal) > 1e-6 || intVal < 0) {
            return; // Invalid solution
          }
          solution[col] = intVal;
        }

        int total = 0;
        for (int p : solution) total += p;
        if (total < best[0]) {
          best[0] = total;
        }
        return;
      }

      int freeVar = freeVars.get(freeIdx);

      // Find reasonable upper bound for this free variable
      int maxVal = 0;
      for (int c = 0; c < joltagePanel.target().size(); c++) {
        maxVal = Math.max(maxVal, joltagePanel.target().get(c));
      }

      for (int val = 0; val <= maxVal; val++) {
        solution[freeVar] = val;

        // Early pruning: check current sum
        int currentSum = 0;
        for (int i = 0; i <= freeIdx; i++) {
          currentSum += solution[freeVars.get(i)];
        }
        if (currentSum >= best[0]) {
          break;
        }

        searchFreeVars(
            aug, pivotCol, numPivotRows, freeVars, freeIdx + 1, solution, best, numButtons);
      }
      solution[freeVar] = 0;
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

    int totalMinimumJoltagePresses() {
      return machines.stream().mapToInt(Machine::findMinimumJoltagePresses).sum();
    }
  }
}
