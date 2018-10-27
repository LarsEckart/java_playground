package lars.katas.salaryslip;

import java.math.BigDecimal;

class Tax {

    private static final BigDecimal TAX_THRESHOLD = BigDecimal.valueOf(11_000);
    private static final BigDecimal HIGH_TAX_THRESHOLD = BigDecimal.valueOf(43_000);
    private static final BigDecimal TWENTY_PERCENT = BigDecimal.valueOf(0.20);
    private static final BigDecimal FOURTY_PERCENT = BigDecimal.valueOf(0.40);

    private final BigDecimal annualGrossSalary;
    private BigDecimal payableTax = BigDecimal.ZERO;
    private BigDecimal taxableIncome = BigDecimal.ZERO;

    public Tax(BigDecimal annualGrossSalary) {
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

    private BigDecimal payableTax() {
        return annualGrossSalary.subtract(TAX_THRESHOLD).multiply(TWENTY_PERCENT);
    }

    private BigDecimal highPayableTax() {
        BigDecimal highTaxAmount = annualGrossSalary.subtract(HIGH_TAX_THRESHOLD);
        BigDecimal highTaxedAmount = highTaxAmount.multiply(FOURTY_PERCENT);
        BigDecimal normalTaxedAmount = (annualGrossSalary.subtract(highTaxAmount).subtract(TAX_THRESHOLD)).multiply(TWENTY_PERCENT);
        return highTaxedAmount.add(normalTaxedAmount);
    }

    private BigDecimal taxableIncome() {
        return annualGrossSalary.subtract(taxFreeAllowance());
    }

    public BigDecimal taxFreeAllowance() {
        return TAX_THRESHOLD;
    }

    public BigDecimal getPayableTax() {
        return payableTax;
    }

    public BigDecimal getTaxableIncome() {
        return taxableIncome;
    }
}
