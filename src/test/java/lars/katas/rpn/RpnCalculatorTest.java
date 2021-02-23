package lars.katas.rpn;

import com.github.larseckart.tcr.TestCommitRevertMainExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(TestCommitRevertMainExtension.class)
public class RpnCalculatorTest {

    @Test
    void new_calculator_has_a_zero_in_its_accumulator() {
        RpnCalculator calculator = new RpnCalculator();
        assertThat(calculator.getAccumulator()).isEqualTo(BigDecimal.ZERO);
    }
}
