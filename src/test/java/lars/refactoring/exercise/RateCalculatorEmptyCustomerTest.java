package lars.refactoring.exercise;

import org.junit.jupiter.api.Test;

public class RateCalculatorEmptyCustomerTest extends RateCalculatorBaseTest {

  @Test
  public void testBlankCustomerHas0Rate() {
    validateRate(0);
  }
}
