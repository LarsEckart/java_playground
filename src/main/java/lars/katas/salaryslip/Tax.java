package lars.katas.salaryslip;

import java.math.BigDecimal;

class Tax {

    private static final BigDecimal TAX_THRESHOLD = BigDecimal.valueOf(11_000);
    private static final BigDecimal TWENTY_PERCENT = BigDecimal.valueOf(0.20);

    private final BigDecimal annualGrossSalary;
    private BigDecimal payableTax = BigDecimal.ZERO;
    private BigDecimal taxableIncome = BigDecimal.ZERO;

    public Tax(BigDecimal annualGrossSalary) {
        this.annualGrossSalary = annualGrossSalary;
        if (isSubjectToTax()) {
            payableTax = payableTax();
            taxableIncome = taxableIncome();
        }
    }

    private boolean isSubjectToTax() {
        return annualGrossSalary.compareTo(TAX_THRESHOLD) == 1;
    }

    private BigDecimal payableTax() {
        if (isSubjectToTax()) {
            return annualGrossSalary.subtract(taxFreeAllowance()).multiply(TWENTY_PERCENT);
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal taxableIncome() {
        if (isSubjectToTax()) {
            return annualGrossSalary.subtract(taxFreeAllowance());
        }
        return BigDecimal.ZERO;
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
