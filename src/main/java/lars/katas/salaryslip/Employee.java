package lars.katas.salaryslip;

import java.math.BigDecimal;

public class Employee {

    private final String id;
    private final String name;
    private final BigDecimal annualGrossSalary;

    public Employee(String id, String name, BigDecimal annualGrossSalary) {
        this.id = id;
        this.name = name;
        this.annualGrossSalary = annualGrossSalary;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAnnualGrossSalary() {
        return annualGrossSalary;
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", annualGrossSalary=" + annualGrossSalary +
            '}';
    }
}
