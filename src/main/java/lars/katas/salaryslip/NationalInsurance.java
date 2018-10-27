package lars.katas.salaryslip;

import java.math.BigDecimal;

class NationalInsurance {

    private static final BigDecimal INSURANCE_CONTRIBUTION_THRESHOLD = BigDecimal.valueOf(8_060.00);

    private final BigDecimal annualGrossSalary;

    public NationalInsurance(BigDecimal annualGrossSalary) {
        this.annualGrossSalary = annualGrossSalary;
    }

    private boolean isSubjectToNationalInsuranceContribution() {
        return annualGrossSalary.compareTo(INSURANCE_CONTRIBUTION_THRESHOLD) == 1;
    }

    public BigDecimal getContribution() {
        if (isSubjectToNationalInsuranceContribution()) {
            BigDecimal amountAboveThreshold = annualGrossSalary.subtract(INSURANCE_CONTRIBUTION_THRESHOLD);
            return amountAboveThreshold.divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(12));
        }
        return BigDecimal.ZERO;
    }
}
