package lars.spielplatz.java12;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class TeeingTest {

  @Test
  void collect_two_downstream_collectors_to_one() {
    Range range =
        Stream.of(2, 8, 4, 12)
            .collect(
                Collectors.teeing(
                    Collectors.minBy(Integer::compareTo),
                    Collectors.maxBy(Integer::compareTo),
                    Range::ofOptional));

    assertThat(range.toString()).isEqualTo("Range{low=2, high=12}");
  }

  static class Range {
    private int low;
    private int high;

    public Range(Integer low, Integer high) {
      this.low = low;
      this.high = high;
    }

    public static Range ofOptional(Optional<Integer> min, Optional<Integer> max) {
      if (min.isEmpty() || max.isEmpty()) {
        throw new IllegalArgumentException("Expected to get two values to create meaningful range");
      }
      return new Range(min.get(), max.get());
    }

    @Override
    public String toString() {
      return "Range{" + "low=" + low + ", high=" + high + '}';
    }
  }
}
