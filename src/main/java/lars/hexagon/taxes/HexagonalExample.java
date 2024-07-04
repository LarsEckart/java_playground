package lars.hexagon.taxes;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

public class HexagonalExample {

  private static final Logger log = getLogger(HexagonalExample.class);

  public static void main(String[] args) {
    ForGettingTaxRates taxRateRepository = new FixedTaxRateRepository();
    ForCalculatingTaxes taxCalculator = new TaxCalculator(taxRateRepository);

    log.info("the tax is " + taxCalculator.taxOn(100));
  }
}
