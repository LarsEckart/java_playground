package lars.katas.salaryslip;

import java.math.BigDecimal;

class Tax {

    private static final BigDecimal TAX_THRESHOLD = BigDecimal.valueOf(11_000);
    private static final BigDecimal TWENTY_PERCENT = BigDecimal.valueOf(0.20);

    private final BigDecimal annualGrossSalary;

    public Tax(BigDecimal annualGrossSalary) {
        this.annualGrossSalary = annualGrossSalary;
    }

    public BigDecimal payableTax() {
        if (isSubjectToTax()) {
            return annualGrossSalary.subtract(taxFreeAllowance()).multiply(TWENTY_PERCENT);
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal taxableIncome() {
        if (isSubjectToTax()) {
            return annualGrossSalary.subtract(taxFreeAllowance());
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal taxFreeAllowance() {
        if (isSubjectToTax()) {
            return TAX_THRESHOLD;
        }
        return BigDecimal.ZERO;
    }

    private boolean isSubjectToTax() {
        return annualGrossSalary.compareTo(TAX_THRESHOLD) == 1;
    }
}
