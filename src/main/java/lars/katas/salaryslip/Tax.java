package lars.katas.salaryslip;

import java.math.BigDecimal;
import java.math.RoundingMode;

class Tax {

    private static final BigDecimal NORMAL_TAX = BigDecimal.valueOf(11_000);
    private static final BigDecimal HIGH_TAX = BigDecimal.valueOf(43_000);
    private static final BigDecimal EXTRA_HIGH_TAX = BigDecimal.valueOf(100_000);

    private static final BigDecimal TWENTY_PERCENT = BigDecimal.valueOf(0.20);
    private static final BigDecimal FOURTY_PERCENT = BigDecimal.valueOf(0.40);

    private final BigDecimal annualGrossSalary;

    private BigDecimal payableTax = BigDecimal.ZERO;
    private BigDecimal taxableIncome = BigDecimal.ZERO;

    Tax(BigDecimal annualGrossSalary) {
        this.annualGrossSalary = annualGrossSalary;
        if (isSubjectTo(NORMAL_TAX)) {
            taxableIncome = taxableIncome();
            payableTax = calculatePayableTax();
        }
    }

    private boolean isSubjectTo(BigDecimal taxThreshold) {
        return annualGrossSalary.doubleValue() > taxThreshold.doubleValue();
    }

    BigDecimal taxFreeAllowance() {
        if (isSubjectToReducedTaxFreeAllowance()) {
            return calculateReducedTaxFreeAllowance();
        }
        return NORMAL_TAX;
    }

    private BigDecimal calculateReducedTaxFreeAllowance() {
        BigDecimal amountAbove100k = annualGrossSalary.subtract(EXTRA_HIGH_TAX);
        BigDecimal amountReducingTaxFreeAllowance = amountAbove100k.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
        BigDecimal reducedTaxFreeAllowance = NORMAL_TAX.subtract(amountReducingTaxFreeAllowance);
        return reducedTaxFreeAllowance.doubleValue() < 0 ? BigDecimal.ZERO : reducedTaxFreeAllowance;
    }

    private BigDecimal taxableIncome() {
        return annualGrossSalary.subtract(taxFreeAllowance());
    }

    private BigDecimal calculatePayableTax() {
        if (isSubjectTo(EXTRA_HIGH_TAX)) {
            BigDecimal above = annualGrossSalary.subtract(EXTRA_HIGH_TAX);
            BigDecimal extraHighTax = above.multiply(FOURTY_PERCENT);
            BigDecimal amountWithHighTaxRate = annualGrossSalary.subtract(above).subtract(HIGH_TAX);
            BigDecimal highTaxedAmount = amountWithHighTaxRate.multiply(FOURTY_PERCENT);
            BigDecimal amountWithNormalTaxRate = annualGrossSalary.subtract(amountWithHighTaxRate).subtract(NORMAL_TAX);
            BigDecimal normalTaxedAmount = amountWithNormalTaxRate.multiply(TWENTY_PERCENT);
            return normalTaxedAmount.add(highTaxedAmount).add(extraHighTax);
        }
        if (isSubjectTo(HIGH_TAX)) {

            BigDecimal amountWithHighTaxRate = annualGrossSalary.subtract(HIGH_TAX);
            BigDecimal highTaxedAmount = amountWithHighTaxRate.multiply(FOURTY_PERCENT);
            BigDecimal amountWithNormalTaxRate = annualGrossSalary.subtract(amountWithHighTaxRate).subtract(NORMAL_TAX);
            BigDecimal normalTaxedAmount = amountWithNormalTaxRate.multiply(TWENTY_PERCENT);
            return normalTaxedAmount.add(highTaxedAmount);
        }
        return annualGrossSalary.subtract(NORMAL_TAX).multiply(TWENTY_PERCENT);
    }

    private boolean isSubjectToReducedTaxFreeAllowance() {
        return annualGrossSalary.doubleValue() > EXTRA_HIGH_TAX.doubleValue();
    }

    BigDecimal getPayableTax() {
        return payableTax;
    }

    BigDecimal getTaxableIncome() {
        return taxableIncome;
    }
}
