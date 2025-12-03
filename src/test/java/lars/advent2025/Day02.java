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
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

class Day02 {

  @Test
  void productId_isInvalid_singleDigitRepeated() {
    assertThat(ProductId.of(11).isInvalid()).isTrue();
    assertThat(ProductId.of(22).isInvalid()).isTrue();
    assertThat(ProductId.of(55).isInvalid()).isTrue();
    assertThat(ProductId.of(99).isInvalid()).isTrue();
  }

  @Test
  void productId_isInvalid_twoDigitRepeated() {
    assertThat(ProductId.of(6464).isInvalid()).isTrue();
    assertThat(ProductId.of(1010).isInvalid()).isTrue();
  }

  @Test
  void productId_isInvalid_threeDigitRepeated() {
    assertThat(ProductId.of(123123).isInvalid()).isTrue();
  }

  @Test
  void productId_isInvalid_validIds() {
    assertThat(ProductId.of(12).isInvalid()).isFalse();
    assertThat(ProductId.of(101).isInvalid()).isFalse();
    assertThat(ProductId.of(1234).isInvalid()).isFalse();
  }

  @Test
  void productIdRange_invalidIds_11to22() {
    ProductIdRange range = ProductIdRange.parse("11-22");
    assertThat(range.invalidIds()).containsExactly(ProductId.of(11), ProductId.of(22));
  }

  @Test
  void productIdRange_invalidIds_95to115() {
    ProductIdRange range = ProductIdRange.parse("95-115");
    assertThat(range.invalidIds()).containsExactly(ProductId.of(99));
  }

  @Test
  void productIdRange_invalidIds_998to1012() {
    ProductIdRange range = ProductIdRange.parse("998-1012");
    assertThat(range.invalidIds()).containsExactly(ProductId.of(1010));
  }

  @Test
  void productIdRange_invalidIds_1188511880to1188511890() {
    ProductIdRange range = ProductIdRange.parse("1188511880-1188511890");
    assertThat(range.invalidIds()).containsExactly(ProductId.of(1188511885L));
  }

  @Test
  void productIdRange_invalidIds_222220to222224() {
    ProductIdRange range = ProductIdRange.parse("222220-222224");
    assertThat(range.invalidIds()).containsExactly(ProductId.of(222222));
  }

  @Test
  void productIdRange_invalidIds_1698522to1698528() {
    ProductIdRange range = ProductIdRange.parse("1698522-1698528");
    assertThat(range.invalidIds()).isEmpty();
  }

  @Test
  void productIdRange_invalidIds_446443to446449() {
    ProductIdRange range = ProductIdRange.parse("446443-446449");
    assertThat(range.invalidIds()).containsExactly(ProductId.of(446446));
  }

  @Test
  void productIdRange_invalidIds_38593856to38593862() {
    ProductIdRange range = ProductIdRange.parse("38593856-38593862");
    assertThat(range.invalidIds()).containsExactly(ProductId.of(38593859L));
  }

  @Test
  void example() {
    String input =
        "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,"
            + "1698522-1698528,446443-446449,38593856-38593862,565653-565659,"
            + "824824821-824824827,2121212118-2121212124";

    ProductDatabase database = ProductDatabase.parse(input);

    assertThat(database.sumOfInvalidIds()).isEqualTo(new BigInteger("1227775554"));
  }

  @Test
  @DisabledIfEnvironmentVariable(named = "CI", matches = ".*")
  @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = ".*")
  void puzzleInput() throws Exception {
    Path inputPath = AdventInputs.ensureDayInput(2025, 2);
    String input = Files.readString(inputPath).trim();

    ProductDatabase database = ProductDatabase.parse(input);
    System.out.println("Day 2 Part 1: " + database.sumOfInvalidIds());
    System.out.println("Day 2 Part 2: " + database.sumOfInvalidIdsPart2());

    assertThat(database.sumOfInvalidIds()).isEqualTo(new BigInteger("5398419778"));
    assertThat(database.sumOfInvalidIdsPart2()).isEqualTo(new BigInteger("15704845910"));
  }

  // Part 2 tests

  @Test
  void productId_isInvalidPart2_repeatedAtLeastTwice() {
    assertThat(ProductId.of(11).isInvalidPart2()).isTrue();
    assertThat(ProductId.of(111).isInvalidPart2()).isTrue();
    assertThat(ProductId.of(1111).isInvalidPart2()).isTrue();
    assertThat(ProductId.of(12341234).isInvalidPart2()).isTrue();
    assertThat(ProductId.of(123123123).isInvalidPart2()).isTrue();
    assertThat(ProductId.of(1212121212L).isInvalidPart2()).isTrue();
    assertThat(ProductId.of(1111111).isInvalidPart2()).isTrue();
  }

  @Test
  void productId_isInvalidPart2_validIds() {
    assertThat(ProductId.of(12).isInvalidPart2()).isFalse();
    assertThat(ProductId.of(101).isInvalidPart2()).isFalse();
    assertThat(ProductId.of(1234).isInvalidPart2()).isFalse();
    assertThat(ProductId.of(12345).isInvalidPart2()).isFalse();
  }

  @Test
  void productIdRange_invalidIdsPart2_95to115() {
    ProductIdRange range = ProductIdRange.parse("95-115");
    assertThat(range.invalidIdsPart2())
        .containsExactlyInAnyOrder(ProductId.of(99), ProductId.of(111));
  }

  @Test
  void productIdRange_invalidIdsPart2_998to1012() {
    ProductIdRange range = ProductIdRange.parse("998-1012");
    assertThat(range.invalidIdsPart2())
        .containsExactlyInAnyOrder(ProductId.of(999), ProductId.of(1010));
  }

  @Test
  void productIdRange_invalidIdsPart2_565653to565659() {
    ProductIdRange range = ProductIdRange.parse("565653-565659");
    assertThat(range.invalidIdsPart2()).containsExactly(ProductId.of(565656));
  }

  @Test
  void productIdRange_invalidIdsPart2_824824821to824824827() {
    ProductIdRange range = ProductIdRange.parse("824824821-824824827");
    assertThat(range.invalidIdsPart2()).containsExactly(ProductId.of(824824824L));
  }

  @Test
  void productIdRange_invalidIdsPart2_2121212118to2121212124() {
    ProductIdRange range = ProductIdRange.parse("2121212118-2121212124");
    assertThat(range.invalidIdsPart2()).containsExactly(ProductId.of(2121212121L));
  }

  @Test
  void examplePart2() {
    String input =
        "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,"
            + "1698522-1698528,446443-446449,38593856-38593862,565653-565659,"
            + "824824821-824824827,2121212118-2121212124";

    ProductDatabase database = ProductDatabase.parse(input);

    assertThat(database.sumOfInvalidIdsPart2()).isEqualTo(new BigInteger("4174379265"));
  }

  // Domain objects

  record ProductId(long value) implements Comparable<ProductId> {

    static ProductId of(long value) {
      return new ProductId(value);
    }

    boolean isInvalid() {
      String s = String.valueOf(value);
      if (s.length() % 2 != 0) {
        return false;
      }
      int half = s.length() / 2;
      return s.substring(0, half).equals(s.substring(half));
    }

    boolean isInvalidPart2() {
      String s = String.valueOf(value);
      int len = s.length();

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

    int digitCount() {
      return String.valueOf(value).length();
    }

    BigInteger toBigInteger() {
      return BigInteger.valueOf(value);
    }

    @Override
    public int compareTo(ProductId other) {
      return Long.compare(this.value, other.value);
    }
  }

  record Pattern(long value) {

    int length() {
      return String.valueOf(value).length();
    }

    ProductId repeat(int times) {
      String patternStr = String.valueOf(value);
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < times; i++) {
        sb.append(patternStr);
      }
      return ProductId.of(Long.parseLong(sb.toString()));
    }

    static List<Pattern> allOfLength(int length) {
      List<Pattern> patterns = new ArrayList<>();
      long min = (length == 1) ? 1 : (long) Math.pow(10, length - 1);
      long max = (long) Math.pow(10, length) - 1;
      for (long v = min; v <= max; v++) {
        patterns.add(new Pattern(v));
      }
      return patterns;
    }
  }

  record ProductIdRange(ProductId start, ProductId end) {

    static ProductIdRange parse(String range) {
      String[] parts = range.split("-");
      return new ProductIdRange(
          ProductId.of(Long.parseLong(parts[0].trim())),
          ProductId.of(Long.parseLong(parts[1].trim())));
    }

    boolean contains(ProductId id) {
      return id.value() >= start.value() && id.value() <= end.value();
    }

    List<ProductId> invalidIds() {
      List<ProductId> result = new ArrayList<>();

      for (int totalLen = start.digitCount(); totalLen <= end.digitCount(); totalLen++) {
        if (totalLen % 2 != 0) continue;

        int halfLen = totalLen / 2;
        for (Pattern pattern : Pattern.allOfLength(halfLen)) {
          ProductId id = pattern.repeat(2);
          if (contains(id)) {
            result.add(id);
          }
        }
      }

      return result;
    }

    List<ProductId> invalidIdsPart2() {
      Set<ProductId> result = new HashSet<>();

      for (int totalLen = start.digitCount(); totalLen <= end.digitCount(); totalLen++) {
        for (int patternLen = 1; patternLen <= totalLen / 2; patternLen++) {
          if (totalLen % patternLen != 0) continue;

          int repetitions = totalLen / patternLen;
          for (Pattern pattern : Pattern.allOfLength(patternLen)) {
            ProductId id = pattern.repeat(repetitions);
            if (contains(id)) {
              result.add(id);
            }
          }
        }
      }

      return new ArrayList<>(result);
    }
  }

  record ProductDatabase(List<ProductIdRange> ranges) {

    static ProductDatabase parse(String input) {
      List<ProductIdRange> ranges = new ArrayList<>();
      for (String range : input.split(",")) {
        if (!range.isBlank()) {
          ranges.add(ProductIdRange.parse(range));
        }
      }
      return new ProductDatabase(ranges);
    }

    BigInteger sumOfInvalidIds() {
      BigInteger sum = BigInteger.ZERO;
      for (ProductIdRange range : ranges) {
        for (ProductId id : range.invalidIds()) {
          sum = sum.add(id.toBigInteger());
        }
      }
      return sum;
    }

    BigInteger sumOfInvalidIdsPart2() {
      BigInteger sum = BigInteger.ZERO;
      for (ProductIdRange range : ranges) {
        for (ProductId id : range.invalidIdsPart2()) {
          sum = sum.add(id.toBigInteger());
        }
      }
      return sum;
    }
  }
}
