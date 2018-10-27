package lars.katas.salaryslip;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SalarySlip {

    private static final BigDecimal TWELVE_MONTHS = BigDecimal.valueOf(12);
    private static final int TWO_DECIMALS = 2;

    private final Employee employee;
    private Tax tax;
    private NationalInsurance nationalInsurance;

    public SalarySlip(Employee employee) {
        this.employee = employee;
        this.tax = new Tax(employee.getAnnualGrossSalary());
        this.nationalInsurance = new NationalInsurance(employee.getAnnualGrossSalary());
    }

    public String getEmployeeId() {
        return employee.getId();
    }

    public String getEmployeeName() {
        return employee.getName();
    }

    public BigDecimal getMonthlyGrossSalary() {
        return toMonthly(employee.getAnnualGrossSalary());
    }

    public BigDecimal getNationalInsuranceContributions() {
        return toMonthly(nationalInsurance.getContribution());
    }

    public BigDecimal getTaxFreeAllowance() {
        return toMonthly(tax.taxFreeAllowance());
    }

    public BigDecimal getTaxableIncome() {
        return toMonthly(tax.getTaxableIncome());
    }

    public BigDecimal getPayableTax() {
        return toMonthly(tax.getPayableTax());
    }

    private BigDecimal toMonthly(BigDecimal annual) {
        return annual.divide(TWELVE_MONTHS, TWO_DECIMALS, RoundingMode.HALF_UP).setScale(2);
    }
}
