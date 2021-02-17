package lars.refactoring.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RateCalculatorIndustorialCustomersTest extends RateCalculatorBaseTest {

  @BeforeEach
  protected void setUp() {
    super.setUp();
    customer.customerType = Customer.INDUSTRIAL;
  }

  @Test
  public void testIndustiralWith0Sites0() {
    validateRate(0);
  }

  @Test
  public void testIndustiralWith1Wit100Kwh() {
    addSite(100);
    validateRate(95);
  }
}
