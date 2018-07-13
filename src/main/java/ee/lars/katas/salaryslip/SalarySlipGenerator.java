package ee.lars.katas.salaryslip;

import java.math.BigDecimal;
import java.math.RoundingMode;

class SalarySlipGenerator {

    public SalarySlip generateFor(Employee employee) {
        BigDecimal monthlyGrossSalary = employee.grossSalary().divide(BigDecimal.valueOf(12L), 2, RoundingMode.HALF_UP);
        return new SalarySlip(employee.ID(), employee.name(), monthlyGrossSalary);
    }
}
