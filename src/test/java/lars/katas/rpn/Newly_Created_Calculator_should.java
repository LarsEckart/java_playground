package lars.katas.rpn;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.larseckart.tcr.CommitOnGreenExtension;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(CommitOnGreenExtension.class)
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
  void allow_multiple_values_to_be_stored() {
    BigDecimal value = new BigDecimal(42);
    BigDecimal value2 = new BigDecimal(2);
    BigDecimal value3 = new BigDecimal(3);
    calculator.setAccumulator(value);
    calculator.enter();
    calculator.setAccumulator(value2);
    calculator.enter();
    calculator.setAccumulator(value3);
    assertThat(calculator.getAccumulator()).isEqualTo(value3);
    calculator.drop();
    assertThat(calculator.getAccumulator()).isEqualTo(value2);
    calculator.drop();
    assertThat(calculator.getAccumulator()).isEqualTo(value);
    calculator.drop();
    assertThat(calculator.getAccumulator()).isEqualTo(BigDecimal.ZERO);
  }
}
