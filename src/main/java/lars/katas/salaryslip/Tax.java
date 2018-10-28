package lars.katas.salaryslip;

import java.math.BigDecimal;

class Tax {

    private static final BigDecimal TAX_THRESHOLD = BigDecimal.valueOf(11_000);
    private static final BigDecimal HIGH_TAX_THRESHOLD = BigDecimal.valueOf(43_000);
    private static final BigDecimal TWENTY_PERCENT = BigDecimal.valueOf(0.20);
    private static final BigDecimal FOURTY_PERCENT = BigDecimal.valueOf(0.40);
    private static final BigDecimal EXTRA_HIGH_TAX_THRESHOLD = BigDecimal.valueOf(100_000);

    private final BigDecimal annualGrossSalary;
    private BigDecimal payableTax = BigDecimal.ZERO;
    private BigDecimal taxableIncome = BigDecimal.ZERO;

    Tax(BigDecimal annualGrossSalary) {
        this.annualGrossSalary = annualGrossSalary;
        if (isSubjectToTax()) {
            taxableIncome = taxableIncome();
            payableTax = isSubjectToHigherTaxRate() ? highPayableTax() : payableTax();
        }
    }

    private boolean isSubjectToTax() {
        return annualGrossSalary.doubleValue() > TAX_THRESHOLD.doubleValue();
    }

    private boolean isSubjectToHigherTaxRate() {
        return annualGrossSalary.doubleValue() > HIGH_TAX_THRESHOLD.doubleValue();
    }

    private boolean isSubjectToExtraHighTaxRate() {
        return annualGrossSalary.doubleValue() > EXTRA_HIGH_TAX_THRESHOLD.doubleValue();
    }

    private BigDecimal payableTax() {
        return annualGrossSalary.subtract(TAX_THRESHOLD).multiply(TWENTY_PERCENT);
    }

    private BigDecimal highPayableTax() {
        if (isSubjectToExtraHighTaxRate()) {
            BigDecimal above = annualGrossSalary.subtract(EXTRA_HIGH_TAX_THRESHOLD);
            BigDecimal extraHighTax = above.multiply(FOURTY_PERCENT);
            BigDecimal amountWithHighTaxRate = annualGrossSalary.subtract(above).subtract(HIGH_TAX_THRESHOLD);
            BigDecimal highTaxedAmount = amountWithHighTaxRate.multiply(FOURTY_PERCENT);
            BigDecimal amountWithNormalTaxRate = annualGrossSalary.subtract(amountWithHighTaxRate).subtract(TAX_THRESHOLD);
            BigDecimal normalTaxedAmount = amountWithNormalTaxRate.multiply(TWENTY_PERCENT);
            return normalTaxedAmount.add(highTaxedAmount).add(extraHighTax);
        }
        BigDecimal amountWithHighTaxRate = annualGrossSalary.subtract(HIGH_TAX_THRESHOLD);
        BigDecimal highTaxedAmount = amountWithHighTaxRate.multiply(FOURTY_PERCENT);
        BigDecimal amountWithNormalTaxRate = annualGrossSalary.subtract(amountWithHighTaxRate).subtract(TAX_THRESHOLD);
        BigDecimal normalTaxedAmount = amountWithNormalTaxRate.multiply(TWENTY_PERCENT);
        return normalTaxedAmount.add(highTaxedAmount);
    }

    private BigDecimal taxableIncome() {
        return annualGrossSalary.subtract(taxFreeAllowance());
    }

    BigDecimal taxFreeAllowance() {
        if (annualGrossSalary.doubleValue() < EXTRA_HIGH_TAX_THRESHOLD.doubleValue()) {
            return TAX_THRESHOLD;
        }

        BigDecimal above = annualGrossSalary.subtract(EXTRA_HIGH_TAX_THRESHOLD);
        BigDecimal toSubtract = above.divide(BigDecimal.valueOf(2));
        return TAX_THRESHOLD.subtract(toSubtract);
    }

    BigDecimal getPayableTax() {
        return payableTax;
    }

    BigDecimal getTaxableIncome() {
        return taxableIncome;
    }
}
