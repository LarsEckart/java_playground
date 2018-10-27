package lars.katas.salaryslip;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SalarySlipGenerator {

    public SalarySlip generateFor(Employee employee) {
        SalarySlip salarySlip = new SalarySlip(employee);
        return salarySlip;
    }
}
