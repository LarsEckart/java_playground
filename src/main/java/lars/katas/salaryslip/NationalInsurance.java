package lars.katas.salaryslip;

import java.math.BigDecimal;

class NationalInsurance {

    private static final BigDecimal INSURANCE_CONTRIBUTION_THRESHOLD = BigDecimal.valueOf(8_060.00);
    private static final BigDecimal HIGH_INSURANCE_CONTRIBUTION_THRESHOLD = BigDecimal.valueOf(43_000.00);

    private final BigDecimal annualGrossSalary;

    NationalInsurance(BigDecimal annualGrossSalary) {
        this.annualGrossSalary = annualGrossSalary;
    }

    private boolean isSubjectToNationalInsuranceContribution() {
        return annualGrossSalary.doubleValue() > INSURANCE_CONTRIBUTION_THRESHOLD.doubleValue();
    }

    private boolean isSubjectToHighNationalInsuranceContribution() {
        return annualGrossSalary.doubleValue() > HIGH_INSURANCE_CONTRIBUTION_THRESHOLD.doubleValue();
    }

    BigDecimal getContribution() {
        if (isSubjectToHighNationalInsuranceContribution()) {
            return highContribution();
        }
        if (isSubjectToNationalInsuranceContribution()) {
            BigDecimal amountAboveThreshold = annualGrossSalary.subtract(INSURANCE_CONTRIBUTION_THRESHOLD);
            return amountAboveThreshold.divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(12));
        }
        return BigDecimal.ZERO;
    }

    BigDecimal highContribution() {
        BigDecimal amountWithHighTaxRate = annualGrossSalary.subtract(HIGH_INSURANCE_CONTRIBUTION_THRESHOLD);
        BigDecimal highTaxedAmount = amountWithHighTaxRate.multiply(BigDecimal.valueOf(0.02));
        BigDecimal amountWithNormalTaxRate = annualGrossSalary.subtract(amountWithHighTaxRate).subtract(INSURANCE_CONTRIBUTION_THRESHOLD);
        BigDecimal normalTaxedAmount = amountWithNormalTaxRate.multiply(BigDecimal.valueOf(0.12));
        return normalTaxedAmount.add(highTaxedAmount);
    }
}
