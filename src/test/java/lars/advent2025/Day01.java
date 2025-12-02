package lars.advent2025;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class Day01 {

  @Test
  void example() {
    List<String> rotations =
        List.of("L68", "L30", "R48", "L5", "R60", "L55", "L1", "L99", "R14", "L82");
    Safe safe = new Safe();
    for (String rotation : rotations) {
      safe = safe.turn(rotation);
    }

    assertThat(safe.zeroCount()).isEqualTo(3);
  }

  @Test
  void invalidDirection() {
    Safe safe = new Safe();
    assertThatThrownBy(() -> safe.turn("X10")).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void puzzleInput() throws Exception {
    List<String> rotations = Files.readAllLines(Path.of("src/test/resources/advent2025/day01.txt"));
    Safe safe = new Safe();
    for (String rotation : rotations) {
      safe = safe.turn(rotation);
    }

    System.out.println("Day 1 Part 1: " + safe.zeroCount());
  }

  enum Direction {
    L {
      int apply(int position, int distance) {
        return (position - distance % 100 + 100) % 100;
      }
    },
    R {
      int apply(int position, int distance) {
        return (position + distance) % 100;
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
  }

  record Safe(int position, int zeroCount) {

    Safe() {
      this(50, 0);
    }

    Safe turn(String rotation) {
      Direction direction = Direction.from(rotation.charAt(0));
      int distance = Integer.parseInt(rotation.substring(1));

      int newPosition = direction.apply(position, distance);
      int newZeroCount = newPosition == 0 ? zeroCount + 1 : zeroCount;
      return new Safe(newPosition, newZeroCount);
    }
  }
}
