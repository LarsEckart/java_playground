package lars.advent2025;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

class Day03 {

  @Test
  void bank_maxJoltage_firstTwoBatteries() {
    BatteryBank bank = BatteryBank.of("987654321111111");

    assertThat(bank.maxJoltage()).isEqualTo(98);
  }

  @Test
  void bank_maxJoltage_firstAndLastBattery() {
    BatteryBank bank = BatteryBank.of("811111111111119");

    assertThat(bank.maxJoltage()).isEqualTo(89);
  }

  @Test
  void bank_maxJoltage_lastTwoBatteries() {
    BatteryBank bank = BatteryBank.of("234234234234278");

    assertThat(bank.maxJoltage()).isEqualTo(78);
  }

  @Test
  void bank_maxJoltage_nonAdjacent() {
    BatteryBank bank = BatteryBank.of("818181911112111");

    assertThat(bank.maxJoltage()).isEqualTo(92);
  }

  @Test
  void example() {
    String input =
        """
        987654321111111
        811111111111119
        234234234234278
        818181911112111
        """;

    BatterySystem system = BatterySystem.parse(input);

    assertThat(system.totalOutputJoltage()).isEqualTo(357);
  }

  // Part 2 tests

  @Test
  void bank_maxJoltage12_example1() {
    BatteryBank bank = BatteryBank.of("987654321111111");

    assertThat(bank.maxJoltage(12)).isEqualTo(new BigInteger("987654321111"));
  }

  @Test
  void bank_maxJoltage12_example2() {
    BatteryBank bank = BatteryBank.of("811111111111119");

    assertThat(bank.maxJoltage(12)).isEqualTo(new BigInteger("811111111119"));
  }

  @Test
  void bank_maxJoltage12_example3() {
    BatteryBank bank = BatteryBank.of("234234234234278");

    assertThat(bank.maxJoltage(12)).isEqualTo(new BigInteger("434234234278"));
  }

  @Test
  void bank_maxJoltage12_example4() {
    BatteryBank bank = BatteryBank.of("818181911112111");

    assertThat(bank.maxJoltage(12)).isEqualTo(new BigInteger("888911112111"));
  }

  @Test
  void examplePart2() {
    String input =
        """
        987654321111111
        811111111111119
        234234234234278
        818181911112111
        """;

    BatterySystem system = BatterySystem.parse(input);

    assertThat(system.totalOutputJoltagePart2()).isEqualTo(new BigInteger("3121910778619"));
  }

  @Test
  @DisabledIfEnvironmentVariable(named = "CI", matches = ".*")
  @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = ".*")
  void puzzleInput() throws Exception {
    Path inputPath = AdventInputs.ensureDayInput(2025, 3);
    String input = Files.readString(inputPath);

    BatterySystem system = BatterySystem.parse(input);
    System.out.println("Day 3 Part 1: " + system.totalOutputJoltage());
    System.out.println("Day 3 Part 2: " + system.totalOutputJoltagePart2());

    assertThat(system.totalOutputJoltage()).isEqualTo(17085);
    assertThat(system.totalOutputJoltagePart2()).isEqualTo(new BigInteger("169408143086082"));
  }

  // Domain objects

  record BatteryBank(String batteries) {

    static BatteryBank of(String batteries) {
      return new BatteryBank(batteries);
    }

    int maxJoltage() {
      int max = 0;
      int length = batteries.length();

      // Try all pairs of positions (i, j) where i < j
      for (int i = 0; i < length; i++) {
        for (int j = i + 1; j < length; j++) {
          int digit1 = batteries.charAt(i) - '0';
          int digit2 = batteries.charAt(j) - '0';
          int joltage = digit1 * 10 + digit2;
          max = Math.max(max, joltage);
        }
      }

      return max;
    }

    BigInteger maxJoltage(int count) {
      // Greedy: for each position, pick the largest digit that leaves enough remaining
      StringBuilder result = new StringBuilder();
      int startIndex = 0;

      for (int digitsNeeded = count; digitsNeeded > 0; digitsNeeded--) {
        // We can pick from startIndex up to (length - digitsNeeded) inclusive
        int maxIndex = batteries.length() - digitsNeeded;
        int bestIndex = startIndex;
        char bestDigit = batteries.charAt(startIndex);

        for (int i = startIndex + 1; i <= maxIndex; i++) {
          if (batteries.charAt(i) > bestDigit) {
            bestDigit = batteries.charAt(i);
            bestIndex = i;
          }
        }

        result.append(bestDigit);
        startIndex = bestIndex + 1;
      }

      return new BigInteger(result.toString());
    }
  }

  record BatterySystem(List<BatteryBank> banks) {

    static BatterySystem parse(String input) {
      List<BatteryBank> banks =
          input.lines().filter(line -> !line.isBlank()).map(BatteryBank::of).toList();
      return new BatterySystem(banks);
    }

    int totalOutputJoltage() {
      return banks.stream().mapToInt(BatteryBank::maxJoltage).sum();
    }

    BigInteger totalOutputJoltagePart2() {
      return banks.stream()
          .map(bank -> bank.maxJoltage(12))
          .reduce(BigInteger.ZERO, BigInteger::add);
    }
  }
}
