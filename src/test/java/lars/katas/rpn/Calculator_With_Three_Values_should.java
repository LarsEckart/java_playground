package lars.katas.rpn;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Calculator_With_Three_Values_should {

  private RpnCalculator calculator;
  BigDecimal value = new BigDecimal(42);
  BigDecimal value2 = new BigDecimal(2);
  BigDecimal value3 = new BigDecimal(3);

  @BeforeEach
  void setUp() {
    calculator = new RpnCalculator();
    calculator.setAccumulator(value);
    calculator.enter();
    calculator.setAccumulator(value2);
    calculator.enter();
    calculator.setAccumulator(value3);
  }

  @Test
  void have_the_last_value_in_its_accumulator() {
    assertThat(calculator.getAccumulator()).isEqualTo(value3);
  }

  @Test
  void have_the_second_to_last_value_after_a_single_drop() {
    calculator.drop();
    assertThat(calculator.getAccumulator()).isEqualTo(value2);
  }

  @Test
  void have_the_second_to_last_value_after_two_drops() {
    calculator.drop();
    calculator.drop();
    assertThat(calculator.getAccumulator()).isEqualTo(value);
  }

  @Test
  void have_zero_after_three_drops() {
    calculator.drop();
    calculator.drop();
    calculator.drop();
    assertThat(calculator.getAccumulator()).isEqualTo(BigDecimal.ZERO);
  }
}
