package lars.refactoring.exercise;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;

public abstract class RateCalculatorBaseTest {

  RateCalculator rateCalculator;
  Customer customer;

  @BeforeEach
  protected void setUp() {
    rateCalculator = new RateCalculator();
    customer = new Customer();
  }

  void validateRate(double expectedRate) {
    double actualRate = rateCalculator.calculateRate(customer);
    assertThat(actualRate).isEqualTo(expectedRate, Offset.offset(0.0001));
  }

  protected void addSite(int kwh) {
    Site site = new Site();
    site.kwh = kwh;
    customer.sites.add(site);
  }

  void setKwh(int kwh) {
    customer.kwh = kwh;
  }
}
