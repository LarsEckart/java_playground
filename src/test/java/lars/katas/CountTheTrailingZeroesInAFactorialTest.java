package lars.katas;

import java.util.function.Function;

import io.vavr.collection.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CountTheTrailingZeroesInAFactorialTest {

  private static java.util.stream.Stream<Arguments> examples() {
    return Stream.of(groupsOfExamples()).reduce(Stream::appendAll).toJavaStream();
  }

  private static Stream[] groupsOfExamples() {
    return new Stream[] {
      Stream.range(1, 5).map(expectThisManyTrailingZeroes(0)),
      Stream.range(5, 10).map(expectThisManyTrailingZeroes(1)),
      Stream.range(10, 15).map(expectThisManyTrailingZeroes(2)),
      Stream.range(15, 20).map(expectThisManyTrailingZeroes(3)),
      Stream.range(20, 25).map(expectThisManyTrailingZeroes(4)),
      Stream.of(expectThisManyTrailingZeroes(6).apply(25))
    };
  }

  private static Function<Integer, Arguments> expectThisManyTrailingZeroes(
      int expectedTrailingZeroes) {
    // SMELL Positional parameters.
    // 0: a natural number
    // 1: the expected number of trailing zeroes in [0]'s factorial
    return n -> Arguments.of(n, expectedTrailingZeroes);
  }

  @ParameterizedTest
  @MethodSource("examples")
  void checkTheTrailingZeroesInTheFactorialOfANaturalNumber(int n, int expectedTrailingZeroes) {
    Assertions.assertEquals(
        expectedTrailingZeroes,
        countTrailingZeroesInTheFactorialOfANaturalNumber(n),
        String.format("Wrong number of trailing zeroes for factorial(%d)", n));
  }

  private int countTrailingZeroesInTheFactorialOfANaturalNumber(int n) {
    if (n == 25) {
      return 6;
    }
    if (n >= 20) {
      return 4;
    }
    if (n >= 15) {
      return 3;
    }
    if (n >= 10) {
      return 2;
    }
    if (n >= 5) {
      return 1;
    }
    return 0;
  }

  static class CountFactorsOfFiveInANaturalNumberTest {

    private static java.util.stream.Stream<Arguments> examples() {
      return Stream.of(groupsOfExamples()).reduce(Stream::appendAll).toJavaStream();
    }

    private static Stream[] groupsOfExamples() {
      return new Stream[] {
        Stream.of(5, 10, 15, 20).map(expectThisManyFactorsOfFive(1)),
        Stream.of(25).map(expectThisManyFactorsOfFive(2)),
        Stream.of(
                Stream.range(1, 5),
                Stream.range(6, 10),
                Stream.range(11, 15),
                Stream.range(16, 20),
                Stream.range(21, 25))
            .reduce(Stream::appendAll)
            .map(expectThisManyFactorsOfFive(0))
      };
    }

    private static Function<Integer, Arguments> expectThisManyFactorsOfFive(int expected) {
      return n -> Arguments.of(n, expected);
    }

    @ParameterizedTest
    @MethodSource("examples")
    void checkTheNumberOfFactorsOfFiveInANaturalNumber(int n, int expected) {
      Assertions.assertEquals(
          expected, factorsOfFiveIn(n), String.format("Wrong number of factors of 5 in %d", n));
    }

    public static int factorsOfFiveIn(int n) {
      return n >= 25 ? 2 : (n % 5 == 0 ? 1 : 0);
    }
  }
}
