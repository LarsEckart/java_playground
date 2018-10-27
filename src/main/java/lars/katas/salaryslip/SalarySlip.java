package lars.katas.salaryslip;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SalarySlip {

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
        return employee.getAnnualGrossSalary().divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
    }
}
