package lars.katas.salaryslip;

import java.math.BigDecimal;
import java.math.RoundingMode;

class Tax {

  private static final BigDecimal TAX_FREE_LIMIT = BigDecimal.valueOf(11_000);
  private static final BigDecimal HIGH_TAX = BigDecimal.valueOf(43_000);
  private static final BigDecimal EXTRA_HIGH_TAX = BigDecimal.valueOf(100_000);
  private static final BigDecimal SUPER_EXTRA_HIGH_TAX = BigDecimal.valueOf(150_000);

  private static final BigDecimal TWENTY_PERCENT = BigDecimal.valueOf(0.20);
  private static final BigDecimal FOURTY_PERCENT = BigDecimal.valueOf(0.40);
  private static final BigDecimal FOURTYFIVE_PERCENT = BigDecimal.valueOf(0.45);

  private final BigDecimal annualGrossSalary;

  private BigDecimal payableTax = BigDecimal.ZERO;
  private BigDecimal taxableIncome = BigDecimal.ZERO;

  Tax(BigDecimal annualGrossSalary) {
    this.annualGrossSalary = annualGrossSalary;
    if (isSubjectTo(TAX_FREE_LIMIT)) {
      taxableIncome = taxableIncome();
      payableTax = calculatePayableTax();
    }
  }

  private boolean isSubjectTo(BigDecimal taxThreshold) {
    return annualGrossSalary.doubleValue() >= taxThreshold.doubleValue();
  }

  BigDecimal taxFreeAllowance() {
    if (isSubjectToReducedTaxFreeAllowance()) {
      return calculateReducedTaxFreeAllowance();
    }
    return TAX_FREE_LIMIT;
  }

  private boolean isSubjectToReducedTaxFreeAllowance() {
    return annualGrossSalary.doubleValue() > EXTRA_HIGH_TAX.doubleValue();
  }

  private BigDecimal calculateReducedTaxFreeAllowance() {
    var amountAbove100k = annualGrossSalary.subtract(EXTRA_HIGH_TAX);
    var amountReducingTaxFreeAllowance =
        amountAbove100k.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
    var reducedTaxFreeAllowance = TAX_FREE_LIMIT.subtract(amountReducingTaxFreeAllowance);
    return reducedTaxFreeAllowance.doubleValue() < 0 ? BigDecimal.ZERO : reducedTaxFreeAllowance;
  }

  private BigDecimal taxableIncome() {
    return annualGrossSalary.subtract(taxFreeAllowance());
  }

  private BigDecimal calculatePayableTax() {
    if (isSubjectTo(SUPER_EXTRA_HIGH_TAX)) {
      var superExtraHigh = annualGrossSalary.subtract(SUPER_EXTRA_HIGH_TAX);
      BigDecimal xxx = superExtraHigh.multiply(FOURTYFIVE_PERCENT);

      var above = annualGrossSalary.subtract(EXTRA_HIGH_TAX).subtract(superExtraHigh);
      var extraHighTax = above.multiply(FOURTY_PERCENT);

      var amountWithHighTaxRate =
          annualGrossSalary.subtract(above).subtract(HIGH_TAX).subtract(superExtraHigh);
      var highTaxedAmount = amountWithHighTaxRate.multiply(FOURTY_PERCENT);

      BigDecimal taxFree = calculateReducedTaxFreeAllowance();
      BigDecimal extraAmountWithHighTax = TAX_FREE_LIMIT.subtract(taxFree);
      var amountWithNormalTaxRate =
          BigDecimal.valueOf(43_000)
              .subtract(extraAmountWithHighTax)
              .subtract(TAX_FREE_LIMIT)
              .add(extraAmountWithHighTax);
      var normalTaxedAmount = amountWithNormalTaxRate.multiply(TWENTY_PERCENT);

      BigDecimal xx = extraAmountWithHighTax.multiply(FOURTY_PERCENT);

      return normalTaxedAmount.add(highTaxedAmount).add(extraHighTax).add(xx).add(xxx);
    }
    if (isSubjectTo(EXTRA_HIGH_TAX)) {
      var above = annualGrossSalary.subtract(EXTRA_HIGH_TAX);
      var extraHighTax = above.multiply(FOURTY_PERCENT);

      var amountWithHighTaxRate = annualGrossSalary.subtract(above).subtract(HIGH_TAX);
      var highTaxedAmount = amountWithHighTaxRate.multiply(FOURTY_PERCENT);

      BigDecimal taxFree = calculateReducedTaxFreeAllowance();
      BigDecimal extraAmountWithHighTax = TAX_FREE_LIMIT.subtract(taxFree);
      var amountWithNormalTaxRate =
          BigDecimal.valueOf(43_000)
              .subtract(extraAmountWithHighTax)
              .subtract(TAX_FREE_LIMIT)
              .add(extraAmountWithHighTax);
      var normalTaxedAmount = amountWithNormalTaxRate.multiply(TWENTY_PERCENT);

      BigDecimal xxx = extraAmountWithHighTax.multiply(FOURTY_PERCENT);

      return normalTaxedAmount.add(highTaxedAmount).add(extraHighTax).add(xxx);
    }

    if (isSubjectTo(HIGH_TAX)) {
      var amountWithHighTaxRate = annualGrossSalary.subtract(HIGH_TAX);
      var highTaxedAmount = amountWithHighTaxRate.multiply(FOURTY_PERCENT);
      var amountWithNormalTaxRate =
          annualGrossSalary.subtract(amountWithHighTaxRate).subtract(TAX_FREE_LIMIT);
      var normalTaxedAmount = amountWithNormalTaxRate.multiply(TWENTY_PERCENT);
      return normalTaxedAmount.add(highTaxedAmount);
    }

    return normalRateTax();
  }

  private BigDecimal normalRateTax() {
    BigDecimal normalTax = BigDecimal.ZERO;
    if (isSubjectTo(TAX_FREE_LIMIT)) {
      var normalTaxAmount =
          BigDecimal.valueOf(
              Math.min(annualGrossSalary.subtract(TAX_FREE_LIMIT).doubleValue(), 32_000d));
      normalTax = normalTaxAmount.multiply(TWENTY_PERCENT);
    }
    return normalTax;
  }

  BigDecimal getPayableTax() {
    return payableTax;
  }

  BigDecimal getTaxableIncome() {
    return taxableIncome;
  }
}
