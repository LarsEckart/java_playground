package lars.katas;

import com.github.larseckart.tcr.TestCommitRevertMainExtension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(TestCommitRevertMainExtension.class)
class StringCalculatorTest {

  @Test
  void return_digit_when_single_number() throws Exception {
    StringCalculator calculator = new StringCalculatorFactory().create();
    int result = calculator.add("1");
    assertThat(result).isEqualTo(1);
  }

  @Test
  void return_digit_when_single_number2() throws Exception {
    StringCalculator calculator = new StringCalculatorFactory().create();
    int result = calculator.add("2");
    assertThat(result).isEqualTo(2);
  }

  @Test
  void return_sum_when_two_numbers_separated_by_comma() throws Exception {
    StringCalculator calculator = new StringCalculatorFactory().create();
    int result = calculator.add("3,4");
    assertThat(result).isEqualTo(7);
  }
}
