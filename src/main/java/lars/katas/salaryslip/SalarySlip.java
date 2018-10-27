package lars.katas.salaryslip;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SalarySlip {

    public static final BigDecimal TWOLVE_MONTHS = BigDecimal.valueOf(12);
    public static final int TWO_DECIMALS = 2;
    private final Employee employee;

    public SalarySlip(Employee employee) {
        this.employee = employee;
    }

    public String getEmployeeId() {
        return employee.getId();
    }

    public String getEmployeeName() {
        return employee.getName();
    }

    public BigDecimal getMonthlyGrossSalary() {
        return monthlyGrossSalary();
    }

    private BigDecimal monthlyGrossSalary() {
        return toMonthly(employee.getAnnualGrossSalary());
    }

    private BigDecimal toMonthly(BigDecimal annual) {
        return annual.divide(TWOLVE_MONTHS, TWO_DECIMALS, RoundingMode.HALF_UP);
    }
}
