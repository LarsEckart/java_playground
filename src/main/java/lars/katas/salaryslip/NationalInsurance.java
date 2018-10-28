package lars.katas.salaryslip;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
            return normalContribution();
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal normalContribution() {
        BigDecimal amountAboveThreshold = annualGrossSalary.subtract(INSURANCE_CONTRIBUTION_THRESHOLD);
        return amountAboveThreshold.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(12));
    }

    BigDecimal highContribution() {
        BigDecimal amountWithHighContribution = annualGrossSalary.subtract(HIGH_INSURANCE_CONTRIBUTION_THRESHOLD);
        BigDecimal highContributionAmount = amountWithHighContribution.multiply(BigDecimal.valueOf(0.02));
        BigDecimal amountWithNormalContribution =
            annualGrossSalary.subtract(amountWithHighContribution).subtract(INSURANCE_CONTRIBUTION_THRESHOLD);
        BigDecimal normalTaxedAmount = amountWithNormalContribution.multiply(BigDecimal.valueOf(0.12));
        return normalTaxedAmount.add(highContributionAmount);
    }
}
