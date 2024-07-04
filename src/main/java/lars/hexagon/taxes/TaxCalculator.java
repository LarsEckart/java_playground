package lars.hexagon.taxes;

public class TaxCalculator implements ForCalculatingTaxes {

  private ForGettingTaxRates taxRateRepository;

  public TaxCalculator(ForGettingTaxRates taxRateRepository) {
    this.taxRateRepository = taxRateRepository;
  }

  @Override
  public double taxOn(double amount) {
    return amount * taxRateRepository.taxRate(amount);
  }
}
