package lars.hexagon.taxes;

public class FixedTaxRateRepository implements ForGettingTaxRates {

  @Override
  public double taxRate(double amount) {
    return 0.15;
  }
}
