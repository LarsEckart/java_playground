package lars.katas.salaryslip;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class SalarySlipGenerator_should {

    private SalarySlipGenerator salarySlipGenerator;

    @Test
    void calculate_monthly_gross_salary() {
        // given
        salarySlipGenerator = new SalarySlipGenerator();
        Employee employee = new Employee("12345", "John J Doe", BigDecimal.valueOf(5_000.00));

        // when
        SalarySlip salarySlip = salarySlipGenerator.generateFor(employee);

        // then
        assertAll(
            () -> assertThat(salarySlip.getEmployeeId()).isEqualTo("12345"),
            () -> assertThat(salarySlip.getEmployeeName()).isEqualTo("John J Doe"),
            () -> assertThat(salarySlip.getMonthlyGrossSalary()).isEqualTo(BigDecimal.valueOf(416.67))
        );
    }
}
