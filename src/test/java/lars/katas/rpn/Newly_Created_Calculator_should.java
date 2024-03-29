package lars.katas.rpn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Newly_Created_Calculator_should {

  private RpnCalculator calculator;

  @BeforeEach
  void setUp() {
    calculator = new RpnCalculator();
  }

  @Test
  void have_zero_in_its_accumulator() {
    assertThat(calculator.getAccumulator()).isEqualTo(BigDecimal.ZERO);
  }

  @Test
  void allow_its_accumulator_to_be_set() {
    BigDecimal value = new BigDecimal(42);
    RpnCalculator calculator = new RpnCalculator();
    calculator.setAccumulator(value);
    assertThat(calculator.getAccumulator()).isEqualTo(value);
  }

  @Test
  void not_throw_an_exception_when_drop_is_called() {
    assertThatCode(() -> calculator.drop()).doesNotThrowAnyException();
  }
}
