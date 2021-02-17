package lars.refactoring.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RateCalculatorIndustrialCustomerWithThreeSitesTest extends RateCalculatorBaseTest {

  @BeforeEach
  protected void setUp() {
    super.setUp();
    customer.customerType = Customer.INDUSTRIAL;
    addSite(300);
    addSite(600);
    addSite(9700);
  }

  @Test
  public void testIndustiralWith3SitesVariableKwh() {
    validateRate(10070);
  }

  @Test
  public void testIndustrialInterruptable() {
    customer.industrialRate = Customer.INTERRUPTABLE;
    validateRate(8480);
  }

  @Test
  public void testIndustrialOneHourNotice() {
    customer.industrialRate = Customer.ONE_HOUR_NOTICE;
    validateRate(9540);
  }

  @Test
  public void testIndustrialDefault() {
    customer.industrialRate = Customer.UNDEFINED;
    validateRate(10070);
  }
}
