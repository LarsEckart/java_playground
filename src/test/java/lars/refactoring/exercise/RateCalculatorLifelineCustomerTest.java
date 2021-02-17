package lars.refactoring.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RateCalculatorLifelineCustomerTest extends RateCalculatorEmptyCustomerTest {

  @BeforeEach
  protected void setUp() {
    super.setUp();
    customer.address = 1;
    customer.customerType = Customer.CONSUMER;
    customer.rate = Customer.LIFELINE;
  }

  @Test
  public void testLifeline200Kwh() {
    setKwh(200);
    validateRate(8);
  }

  @Test
  public void testLifeline100Kwh() {
    setKwh(100);
    validateRate(3);
  }

  @Test
  public void testLifeline500KwhSameAsTerritorial() {
    setKwh(500);
    validateRate(30);
  }
}
