package lars.katas;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
class StringCalculatorTest {

  @Test
  void return_digit_when_single_number() {
    StringCalculator calculator = new StringCalculatorFactory().create();
    int result = calculator.add("1");
    assertThat(result).isEqualTo(1);
  }

  @Test
  void return_digit_when_single_number2() {
    StringCalculator calculator = new StringCalculatorFactory().create();
    int result = calculator.add("2");
    assertThat(result).isEqualTo(2);
  }

  @Test
  void return_sum_when_two_numbers_separated_by_comma() {
    StringCalculator calculator = new StringCalculatorFactory().create();
    int result = calculator.add("3,4");
    assertThat(result).isEqualTo(7);
  }

  @Test
  void new_line_also_separates_like_comma() {
    StringCalculator calculator = new StringCalculatorFactory().create();
    int result = calculator.add("1\n2,3");
    assertThat(result).isEqualTo(6);
  }
}
