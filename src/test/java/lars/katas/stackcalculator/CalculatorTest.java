package lars.katas.stackcalculator;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorTest {

  @Test
  void first_example() {
      Calculator calculator = new Calculator();

    calculator.press("3");
    calculator.push();
    calculator.press("7");
    calculator.push();
    calculator.press("2");
    calculator.add();
    calculator.multiply();

    assertThat(calculator.pop()).isEqualTo(new BigInteger("27"));;
  }

  @Test
  void second_example() {
      Calculator calculator = new Calculator();

    calculator.press("3");
    calculator.push();
    calculator.press("7");
      calculator.multiply();
      calculator.push();
      calculator.press("2");
      calculator.add();

    assertThat(calculator.pop()).isEqualTo(new BigInteger("23"));;
  }
}