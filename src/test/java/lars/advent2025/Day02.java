package lars.advent2025;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class Day02 {

  @Test
  void isInvalidId_singleDigitRepeated() {
    assertThat(isInvalidId(11)).isTrue();
    assertThat(isInvalidId(22)).isTrue();
    assertThat(isInvalidId(55)).isTrue();
    assertThat(isInvalidId(99)).isTrue();
  }

  @Test
  void isInvalidId_twoDigitRepeated() {
    assertThat(isInvalidId(6464)).isTrue();
    assertThat(isInvalidId(1010)).isTrue();
  }

  @Test
  void isInvalidId_threeDigitRepeated() {
    assertThat(isInvalidId(123123)).isTrue();
  }

  @Test
  void isInvalidId_validIds() {
    assertThat(isInvalidId(12)).isFalse();
    assertThat(isInvalidId(101)).isFalse(); // odd length
    assertThat(isInvalidId(1234)).isFalse();
  }

  @Test
  void findInvalidIdsInRange_11to22() {
    List<Long> invalid = findInvalidIdsInRange(11, 22);
    assertThat(invalid).containsExactly(11L, 22L);
  }

  @Test
  void findInvalidIdsInRange_95to115() {
    List<Long> invalid = findInvalidIdsInRange(95, 115);
    assertThat(invalid).containsExactly(99L);
  }

  @Test
  void findInvalidIdsInRange_998to1012() {
    List<Long> invalid = findInvalidIdsInRange(998, 1012);
    assertThat(invalid).containsExactly(1010L);
  }

  @Test
  void findInvalidIdsInRange_1188511880to1188511890() {
    List<Long> invalid = findInvalidIdsInRange(1188511880L, 1188511890L);
    assertThat(invalid).containsExactly(1188511885L);
  }

  @Test
  void findInvalidIdsInRange_222220to222224() {
    List<Long> invalid = findInvalidIdsInRange(222220, 222224);
    assertThat(invalid).containsExactly(222222L);
  }

  @Test
  void findInvalidIdsInRange_1698522to1698528() {
    List<Long> invalid = findInvalidIdsInRange(1698522, 1698528);
    assertThat(invalid).isEmpty();
  }

  @Test
  void findInvalidIdsInRange_446443to446449() {
    List<Long> invalid = findInvalidIdsInRange(446443, 446449);
    assertThat(invalid).containsExactly(446446L);
  }

  @Test
  void findInvalidIdsInRange_38593856to38593862() {
    List<Long> invalid = findInvalidIdsInRange(38593856L, 38593862L);
    assertThat(invalid).containsExactly(38593859L);
  }

  @Test
  void example() {
    String input =
        "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,"
            + "1698522-1698528,446443-446449,38593856-38593862,565653-565659,"
            + "824824821-824824827,2121212118-2121212124";

    BigInteger sum = sumInvalidIds(input);

    assertThat(sum).isEqualTo(new BigInteger("1227775554"));
  }

  @Test
  void puzzleInput() throws Exception {
    String input = Files.readString(Path.of("src/test/resources/advent2025/day02.txt")).trim();

    BigInteger sum = sumInvalidIds(input);
    System.out.println("Day 2 Part 1: " + sum);

    BigInteger sum2 = sumInvalidIdsPart2(input);
    System.out.println("Day 2 Part 2: " + sum2);
  }

  // Part 2 tests

  @Test
  void isInvalidIdPart2_repeatedAtLeastTwice() {
    assertThat(isInvalidIdPart2(11)).isTrue(); // 1 repeated 2 times
    assertThat(isInvalidIdPart2(111)).isTrue(); // 1 repeated 3 times
    assertThat(isInvalidIdPart2(1111)).isTrue(); // 1 repeated 4 times
    assertThat(isInvalidIdPart2(12341234)).isTrue(); // 1234 repeated 2 times
    assertThat(isInvalidIdPart2(123123123)).isTrue(); // 123 repeated 3 times
    assertThat(isInvalidIdPart2(1212121212L)).isTrue(); // 12 repeated 5 times
    assertThat(isInvalidIdPart2(1111111)).isTrue(); // 1 repeated 7 times
  }

  @Test
  void isInvalidIdPart2_validIds() {
    assertThat(isInvalidIdPart2(12)).isFalse();
    assertThat(isInvalidIdPart2(101)).isFalse();
    assertThat(isInvalidIdPart2(1234)).isFalse();
    assertThat(isInvalidIdPart2(12345)).isFalse();
  }

  @Test
  void findInvalidIdsInRangePart2_95to115() {
    List<Long> invalid = findInvalidIdsInRangePart2(95, 115);
    assertThat(invalid).containsExactlyInAnyOrder(99L, 111L);
  }

  @Test
  void findInvalidIdsInRangePart2_998to1012() {
    List<Long> invalid = findInvalidIdsInRangePart2(998, 1012);
    assertThat(invalid).containsExactlyInAnyOrder(999L, 1010L);
  }

  @Test
  void findInvalidIdsInRangePart2_565653to565659() {
    List<Long> invalid = findInvalidIdsInRangePart2(565653, 565659);
    assertThat(invalid).containsExactly(565656L);
  }

  @Test
  void findInvalidIdsInRangePart2_824824821to824824827() {
    List<Long> invalid = findInvalidIdsInRangePart2(824824821L, 824824827L);
    assertThat(invalid).containsExactly(824824824L);
  }

  @Test
  void findInvalidIdsInRangePart2_2121212118to2121212124() {
    List<Long> invalid = findInvalidIdsInRangePart2(2121212118L, 2121212124L);
    assertThat(invalid).containsExactly(2121212121L);
  }

  @Test
  void examplePart2() {
    String input =
        "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,"
            + "1698522-1698528,446443-446449,38593856-38593862,565653-565659,"
            + "824824821-824824827,2121212118-2121212124";

    BigInteger sum = sumInvalidIdsPart2(input);

    // 11+22 + 99+111 + 999+1010 + 1188511885 + 222222 + 446446 + 38593859 + 565656 + 824824824 +
    // 2121212121
    assertThat(sum).isEqualTo(new BigInteger("4174379265"));
  }

  private BigInteger sumInvalidIdsPart2(String input) {
    BigInteger sum = BigInteger.ZERO;
    String[] ranges = input.split(",");
    for (String range : ranges) {
      if (range.isBlank()) continue;
      String[] parts = range.split("-");
      long start = Long.parseLong(parts[0].trim());
      long end = Long.parseLong(parts[1].trim());
      List<Long> invalidIds = findInvalidIdsInRangePart2(start, end);
      for (Long id : invalidIds) {
        sum = sum.add(BigInteger.valueOf(id));
      }
    }
    return sum;
  }

  private BigInteger sumInvalidIds(String input) {
    BigInteger sum = BigInteger.ZERO;
    String[] ranges = input.split(",");
    for (String range : ranges) {
      if (range.isBlank()) continue;
      String[] parts = range.split("-");
      long start = Long.parseLong(parts[0].trim());
      long end = Long.parseLong(parts[1].trim());
      List<Long> invalidIds = findInvalidIdsInRange(start, end);
      for (Long id : invalidIds) {
        sum = sum.add(BigInteger.valueOf(id));
      }
    }
    return sum;
  }

  private boolean isInvalidId(long id) {
    String s = String.valueOf(id);
    if (s.length() % 2 != 0) {
      return false;
    }
    int half = s.length() / 2;
    return s.substring(0, half).equals(s.substring(half));
  }

  private boolean isInvalidIdPart2(long id) {
    String s = String.valueOf(id);
    int len = s.length();

    // Try all possible pattern lengths that divide evenly into the total length
    // and result in at least 2 repetitions
    for (int patternLen = 1; patternLen <= len / 2; patternLen++) {
      if (len % patternLen != 0) continue;

      String pattern = s.substring(0, patternLen);
      boolean matches = true;
      for (int i = patternLen; i < len; i += patternLen) {
        if (!s.substring(i, i + patternLen).equals(pattern)) {
          matches = false;
          break;
        }
      }
      if (matches) {
        return true;
      }
    }
    return false;
  }

  private List<Long> findInvalidIdsInRange(long start, long end) {
    List<Long> result = new ArrayList<>();

    // Instead of iterating all numbers (could be huge ranges),
    // we generate all invalid IDs that could fall in the range.
    // Invalid IDs are formed by repeating a sequence twice.

    // Determine the digit lengths we need to consider
    int startLen = String.valueOf(start).length();
    int endLen = String.valueOf(end).length();

    // For each possible total length (must be even)
    for (int totalLen = startLen; totalLen <= endLen; totalLen++) {
      if (totalLen % 2 != 0) continue;

      int halfLen = totalLen / 2;

      // Generate all half-patterns of this length
      long minHalf = (halfLen == 1) ? 1 : (long) Math.pow(10, halfLen - 1);
      long maxHalf = (long) Math.pow(10, halfLen) - 1;

      for (long half = minHalf; half <= maxHalf; half++) {
        String halfStr = String.valueOf(half);
        String fullStr = halfStr + halfStr;
        long id = Long.parseLong(fullStr);

        if (id >= start && id <= end) {
          result.add(id);
        }
      }
    }

    return result;
  }

  private List<Long> findInvalidIdsInRangePart2(long start, long end) {
    Set<Long> result = new HashSet<>();

    int startLen = String.valueOf(start).length();
    int endLen = String.valueOf(end).length();

    // For each total length in range
    for (int totalLen = startLen; totalLen <= endLen; totalLen++) {
      // For each pattern length that divides totalLen and gives at least 2 reps
      for (int patternLen = 1; patternLen <= totalLen / 2; patternLen++) {
        if (totalLen % patternLen != 0) continue;

        int repetitions = totalLen / patternLen;

        // Generate all patterns of this length (no leading zeros)
        long minPattern = (patternLen == 1) ? 1 : (long) Math.pow(10, patternLen - 1);
        long maxPattern = (long) Math.pow(10, patternLen) - 1;

        for (long pattern = minPattern; pattern <= maxPattern; pattern++) {
          String patternStr = String.valueOf(pattern);
          StringBuilder sb = new StringBuilder();
          for (int r = 0; r < repetitions; r++) {
            sb.append(patternStr);
          }
          long id = Long.parseLong(sb.toString());

          if (id >= start && id <= end) {
            result.add(id);
          }
        }
      }
    }

    return new ArrayList<>(result);
  }
}
