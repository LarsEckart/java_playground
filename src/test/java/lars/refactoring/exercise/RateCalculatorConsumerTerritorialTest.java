package lars.refactoring.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RateCalculatorConsumerTerritorialTest extends RateCalculatorBaseTest {

  @BeforeEach
  protected void setUp() {
    super.setUp();
    customer.customerType = Customer.CONSUMER;
    customer.rate = Customer.TERITORIAL;
    setKwh(500);
  }

  @Test
  public void testTerritorial500KhWithAddressCategory1() {
    customer.address = 1;
    validateRate(30);
  }

  @Test
  public void testTerritorial500KwhAddressCategory3() {
    customer.address = 3;
    validateRate(32.5);
  }
}
