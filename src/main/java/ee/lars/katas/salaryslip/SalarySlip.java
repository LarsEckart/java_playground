package ee.lars.katas.salaryslip;

import java.math.BigDecimal;
import java.util.Objects;

class SalarySlip {

    private int employeeId;
    private final String name;
    private final BigDecimal grossSalary;

    public SalarySlip(int employeeId, String name, BigDecimal grossSalary) {
        this.employeeId = employeeId;
        this.name = name;
        this.grossSalary = grossSalary;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalarySlip that = (SalarySlip) o;
        return employeeId == that.employeeId &&
                Objects.equals(name, that.name) &&
                (grossSalary.compareTo(that.grossSalary)) == 0;
    }

    @Override public int hashCode() {

        return Objects.hash(employeeId, name, grossSalary);
    }
}
