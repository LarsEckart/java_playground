package lars.junit5;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CsvSourceExampleTests {

  @ParameterizedTest
  @CsvSource({"hello world, 11", "JUnit 5,      7"})
  void calculatesPhraseLength(String phrase, int expectedLength) {
    assertThat(phrase.length()).isEqualTo(expectedLength);
  }

  @ParameterizedTest
  @CsvSource(textBlock = """
          hello world, 11
          JUnit 5,      7
      """)
  void calculatesPhraseLengthWithTextBlocks(String phrase, int expectedLength) {
    assertThat(phrase.length()).isEqualTo(expectedLength);
  }

  @ParameterizedTest
  @CsvSource(
      delimiter = '|',
      textBlock =
          """
                  Hello world!    | Hallo Welt!   | 12
                  Spock           | JUnit Jupiter | 13
                  ''              | ''            |  0
              """)
  void calculatesMaxLength(String phrase1, String phrase2, int expected) {
    int actual = Math.max(phrase1.length(), phrase2.length());
    assertThat(actual).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource(
      delimiterString = "->",
      textBlock =
          """
                  fooBar        -> FooBar
                  junit_jupiter -> JunitJupiter
                  CsvSource     -> CsvSource
              """)
  void convertsToUpperCamelCase(String input, String expected) {
    String converted = CaseConverter.toUpperCamelCase(input);
    assertThat(converted).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource(
      delimiterString = "maps to",
      textBlock =
          """
              'foo'    maps to  'bar'
              'junit'  maps to  'jupiter'
          """)
  void shouldMapPair(String input, String expected) {
    String actual = PairMapper.map(input);
    assertThat(actual).isEqualTo(expected);
  }

  @ParameterizedTest(name = "{index} => calculates the sum of {0}: ({1}, {2})")
  @CsvSource(
      delimiter = '|',
      textBlock =
          """
    positive numbers      |   10  |      6  |   16
    positive and negative |   -4  |      2  |   -2
    negative numbers      |   -6  |   -100  | -106
""")
  void calculatesSum(String description, int a, int b, int expectedSum) {
    int actual = Calculator.sum(a, b);
    assertThat(actual).isEqualTo(expectedSum);
  }

  @DisplayName("Calculates the sum of:")
  @ParameterizedTest(name = "{index} => {0}: ({1}, {2})")
  @CsvSource(
      delimiter = '|',
      textBlock =
          """
    positive numbers      |   10  |      6  |   16
    positive and negative |   -4  |      2  |   -2
    negative numbers      |   -6  |   -100  | -106
""")
  void calculatesSumBetterDescription(String description, int a, int b, int expectedSum) {
    int actual = Calculator.sum(a, b);
    assertThat(actual).isEqualTo(expectedSum);
  }

  static class CaseConverter {

    static String toUpperCamelCase(String input) {
      char[] charArray = input.toCharArray();
      boolean capitalizeNext = true;
      for (int i = 0; i < charArray.length; i++) {
        if (charArray[i] == '_') {
          capitalizeNext = true;
        } else if (capitalizeNext) {
          charArray[i] = Character.toUpperCase(charArray[i]);
          capitalizeNext = false;
        }
      }
      return new String(charArray).replace("_", "");
    }
  }

  static class PairMapper {

    static String map(String input) {
      return Map.of("foo", "bar", "junit", "jupiter").get(input);
    }
  }

  static class Calculator {

    static int sum(int a, int b) {
      return a + b;
    }
  }
}
