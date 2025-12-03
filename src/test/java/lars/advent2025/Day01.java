package lars.advent2025;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

class Day01 {

  @Test
  void example_part1() {
    List<String> rotations =
        List.of("L68", "L30", "R48", "L5", "R60", "L55", "L1", "L99", "R14", "L82");
    Safe safe = new Safe();
    for (String rotation : rotations) {
      safe = safe.turn(rotation);
    }

    assertThat(safe.zeroCount()).isEqualTo(3);
  }

  @Test
  void example_part2() {
    List<String> rotations =
        List.of("L68", "L30", "R48", "L5", "R60", "L55", "L1", "L99", "R14", "L82");
    Safe safe = new Safe();
    for (String rotation : rotations) {
      safe = safe.turn(rotation);
    }

    assertThat(safe.clickCount()).isEqualTo(6);
  }

  @Test
  void r1000_from_50_passes_zero_10_times() {
    Safe safe = new Safe().turn("R1000");

    assertThat(safe.clickCount()).isEqualTo(10);
    assertThat(safe.position()).isEqualTo(50);
  }

  @Test
  void invalidDirection() {
    Safe safe = new Safe();

    assertThatThrownBy(() -> safe.turn("X10")).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisabledIfEnvironmentVariable(named = "CI", matches = ".*")
  @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = ".*")
  void puzzleInput() throws Exception {
    Path input = AdventInputs.ensureDayInput(2025, 1);
    List<String> rotations = Files.readAllLines(input);
    Safe safe = new Safe();
    for (String rotation : rotations) {
      safe = safe.turn(rotation);
    }

    System.out.println("Day 1 Part 1: " + safe.zeroCount());
    System.out.println("Day 1 Part 2: " + safe.clickCount());

    assertThat(safe.zeroCount()).isEqualTo(1100);
    assertThat(safe.clickCount()).isEqualTo(6358);
  }

  record Dial(int position) {

    static final int SIZE = 100;

    Dial {
      if (position < 0 || position >= SIZE) {
        throw new IllegalArgumentException("Position must be 0-99, was: " + position);
      }
    }

    Dial() {
      this(50);
    }

    Dial turn(Direction direction, int distance) {
      int newPosition = direction.apply(position, distance);
      return new Dial(newPosition);
    }

    boolean isAtZero() {
      return position == 0;
    }
  }

  enum Direction {
    L {
      int apply(int position, int distance) {
        return (position - distance % Dial.SIZE + Dial.SIZE) % Dial.SIZE;
      }

      int countZeroCrossings(int position, int distance) {
        // Moving left: count how many times we land on 0
        // From position, we hit 0 after `position` clicks, then every SIZE clicks
        // E.g., from 1, L1 -> lands on 0 (1 click to reach 0)
        if (position == 0) {
          // Starting at 0, we hit 0 again after SIZE clicks
          return distance / Dial.SIZE;
        }
        int clicksToFirstZero = position;
        if (distance < clicksToFirstZero) {
          return 0;
        }
        return 1 + (distance - clicksToFirstZero) / Dial.SIZE;
      }
    },
    R {
      int apply(int position, int distance) {
        return (position + distance) % Dial.SIZE;
      }

      int countZeroCrossings(int position, int distance) {
        // Moving right: count how many times we land on 0
        // From position, we hit 0 after (SIZE-position) clicks, then every SIZE clicks
        // E.g., from 52, R48 -> lands on 0 (100-52=48 clicks to reach 0)
        if (position == 0) {
          // Starting at 0, we hit 0 again after SIZE clicks
          return distance / Dial.SIZE;
        }
        int clicksToFirstZero = Dial.SIZE - position;
        if (distance < clicksToFirstZero) {
          return 0;
        }
        return 1 + (distance - clicksToFirstZero) / Dial.SIZE;
      }
    };

    static Direction from(char c) {
      return switch (c) {
        case 'L' -> L;
        case 'R' -> R;
        default -> throw new IllegalArgumentException("Invalid direction: " + c);
      };
    }

    abstract int apply(int position, int distance);

    abstract int countZeroCrossings(int position, int distance);
  }

  record Safe(Dial dial, int zeroCount, int clickCount) {

    Safe() {
      this(new Dial(), 0, 0);
    }

    int position() {
      return dial.position();
    }

    Safe turn(String rotation) {
      Direction direction = Direction.from(rotation.charAt(0));
      int distance = Integer.parseInt(rotation.substring(1));

      Dial newDial = dial.turn(direction, distance);
      int newZeroCount = newDial.isAtZero() ? zeroCount + 1 : zeroCount;
      int newClickCount = clickCount + direction.countZeroCrossings(dial.position(), distance);
      return new Safe(newDial, newZeroCount, newClickCount);
    }
  }
}
