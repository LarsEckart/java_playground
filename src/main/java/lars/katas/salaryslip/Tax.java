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
        var amountAbove100k = annualGrossSalary.subtract(EXTRA_HIGH_TAX);
        var amountReducingTaxFreeAllowance = amountAbove100k.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
        var reducedTaxFreeAllowance = NORMAL_TAX.subtract(amountReducingTaxFreeAllowance);
        return reducedTaxFreeAllowance;
    }

    private BigDecimal taxableIncome() {
        return annualGrossSalary.subtract(taxFreeAllowance());
    }

    private BigDecimal calculatePayableTax() {
        // TODO: refactor here
        if (isSubjectTo(EXTRA_HIGH_TAX)) {
            var above = annualGrossSalary.subtract(EXTRA_HIGH_TAX);
            var extraHighTax = above.multiply(FOURTY_PERCENT);
            var amountWithHighTaxRate = annualGrossSalary.subtract(above).subtract(HIGH_TAX);
            var highTaxedAmount = amountWithHighTaxRate.multiply(FOURTY_PERCENT);
            var amountWithNormalTaxRate = annualGrossSalary.subtract(amountWithHighTaxRate).subtract(NORMAL_TAX);
            var normalTaxedAmount = amountWithNormalTaxRate.multiply(TWENTY_PERCENT);
            return normalTaxedAmount.add(highTaxedAmount).add(extraHighTax);
        }
        if (isSubjectTo(HIGH_TAX)) {
            var amountWithHighTaxRate = annualGrossSalary.subtract(HIGH_TAX);
            var highTaxedAmount = amountWithHighTaxRate.multiply(FOURTY_PERCENT);
            var amountWithNormalTaxRate = annualGrossSalary.subtract(amountWithHighTaxRate).subtract(NORMAL_TAX);
            var normalTaxedAmount = amountWithNormalTaxRate.multiply(TWENTY_PERCENT);
            return normalTaxedAmount.add(highTaxedAmount);
        }
        return normalRateTax();
    }

    private BigDecimal normalRateTax() {
        BigDecimal normalTax = BigDecimal.ZERO;
        if (isSubjectTo(NORMAL_TAX)) {
            var normalTaxAmount = BigDecimal.valueOf(Math.min(annualGrossSalary.subtract(NORMAL_TAX).doubleValue(), 32_000d));
            normalTax = normalTaxAmount.multiply(TWENTY_PERCENT);
        }
        return normalTax;
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
