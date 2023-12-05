package lars.advent2023;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.regex.Pattern;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Day1 {

  static class CalibrationValue {

    private final String input;

    private CalibrationValue(String input) {
      this.input = input;
    }

    public int sumOfCalibrationValues() {
      return input
          .lines()
          .map(this::spelledOutWordToNumber)
          .map(l -> combine(findFirstDigit(l), findLastDigit(l)))
          .reduce(0, Integer::sum);
    }

    private int findFirstDigit(String input) {
      Pattern p = Pattern.compile("\\d");
      return p.matcher(input).results().map(m -> Integer.parseInt(m.group())).findFirst().get();
    }

    private int findLastDigit(String input) {
      Pattern p = Pattern.compile("\\d");
      return p.matcher(input)
          .results()
          .map(m -> Integer.parseInt(m.group()))
          .reduce((a, b) -> b)
          .get();
    }

    private int combine(int a, int b) {
      return Integer.parseInt("" + a + b);
    }

    private String spelledOutWordToNumber(String input) {

      var dictionary = new HashMap<String, String>();
      dictionary.put("one", "on1e"); // to account for overlaps line oneight
      dictionary.put("two", "tw2o");
      dictionary.put("three", "thre3e");
      dictionary.put("four", "4");
      dictionary.put("five", "fiv5e");
      dictionary.put("six", "6");
      dictionary.put("seven", "7");
      dictionary.put("eight", "eigh8t");
      dictionary.put("nine", "nin9e");

      String result = input;

      for (String word : dictionary.keySet()) {
        result = result.replaceAll(word, dictionary.get(word));
      }
      return result;
    }
  }

  @ParameterizedTest
  @CsvSource({
    "one111jxlmc7tvklrmhdpsix,16",
    "1dtwo,12",
    "nine4sevenpnbbztpvkbgztb,97",
    "4bvnccbdh4onefztdrpq62vvbnvpxxvgrngnfjgfk,42",
    "eighthree,83",
  })
  void testSingleLines(String input, int expected) {
    assertThat(new CalibrationValue(input).sumOfCalibrationValues()).isEqualTo(expected);
  }
}
