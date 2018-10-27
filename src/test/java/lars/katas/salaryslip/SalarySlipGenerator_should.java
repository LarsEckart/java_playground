package lars.katas.salaryslip;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class SalarySlipGenerator_should {

    private static final String ANY_ID = "12345";
    private static final String ANY_NAME = "John J Doe";
    private static final BigDecimal EXPECTED_MONTHLY_GROSS_SALARY = BigDecimal.valueOf(416.67);

    private SalarySlipGenerator salarySlipGenerator;

    @Test
    void calculate_monthly_gross_salary() {
        // given
        salarySlipGenerator = new SalarySlipGenerator();
        Employee employee = new Employee(ANY_ID, ANY_NAME, BigDecimal.valueOf(5_000.00));

        // when
        SalarySlip salarySlip = salarySlipGenerator.generateFor(employee);

        // then
        assertAll(
            () -> assertThat(salarySlip.getEmployeeId()).isEqualTo(ANY_ID),
            () -> assertThat(salarySlip.getEmployeeName()).isEqualTo(ANY_NAME),
            () -> assertThat(salarySlip.getMonthlyGrossSalary()).isEqualTo(EXPECTED_MONTHLY_GROSS_SALARY)
        );
    }
}
