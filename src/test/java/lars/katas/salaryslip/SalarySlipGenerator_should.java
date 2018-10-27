package lars.katas.salaryslip;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class SalarySlipGenerator_should {

    private static final String ANY_ID = "12345";
    private static final String ANY_NAME = "John J Doe";
    private static final BigDecimal EXPECTED_MONTHLY_GROSS_SALARY = BigDecimal.valueOf(416.67);

    private SalarySlipGenerator salarySlipGenerator = new SalarySlipGenerator();

    @ParameterizedTest
    @MethodSource("grossSalaryProvider")
    void generate_slip_with_monthly_gross_salary(BigDecimal annualGrossSalary, BigDecimal monthlyGrossSalary) {
        // given
        Employee employee = employeeWithAnnualSalaryOf(annualGrossSalary);

        // when
        SalarySlip salarySlip = salarySlipGenerator.generateFor(employee);

        // then
        assertAll(
            () -> assertThat(salarySlip.getEmployeeId()).isEqualTo(ANY_ID),
            () -> assertThat(salarySlip.getEmployeeName()).isEqualTo(ANY_NAME),
            () -> assertThat(salarySlip.getMonthlyGrossSalary()).isEqualTo(monthlyGrossSalary)
        );
    }

    static Stream<Arguments> grossSalaryProvider() {
        return Stream.of(
            Arguments.of(BigDecimal.valueOf(5_000.000), EXPECTED_MONTHLY_GROSS_SALARY),
            Arguments.of(BigDecimal.valueOf(9_060.00), BigDecimal.valueOf(755.00).setScale(2))
        );
    }

    @ParameterizedTest
    @MethodSource("grossSalaryAbove8060")
    void generate_slip_with_national_insurance_contributions_when_annual_salary_above_8060(
        BigDecimal annualGrossSalary, BigDecimal insuranceContribution) {
        // given
        Employee employee = employeeWithAnnualSalaryOf(annualGrossSalary);

        // when
        SalarySlip salarySlip = salarySlipGenerator.generateFor(employee);

        // then
        assertThat(salarySlip.getNationalInsuranceContributions()).isEqualTo(insuranceContribution);
    }

    static Stream<Arguments> grossSalaryAbove8060() {
        return Stream.of(
            Arguments.of(BigDecimal.valueOf(9_060.00), BigDecimal.valueOf(10.00).setScale(2)),
            Arguments.of(BigDecimal.valueOf(12_000.00), BigDecimal.valueOf(39.40).setScale(2))
        );
    }

    @Test
    void generate_slip_with_national_insurance_contributions_zero_when_annual_salary_below_8060() {
        // given
        Employee employee = employeeWithAnnualSalaryOf(BigDecimal.valueOf(5_000));

        // when
        SalarySlip salarySlip = salarySlipGenerator.generateFor(employee);

        // then
        assertThat(salarySlip.getNationalInsuranceContributions()).isEqualTo(BigDecimal.ZERO);
    }

    // TODO: accept int
    private Employee employeeWithAnnualSalaryOf(BigDecimal annualGrossSalary) {
        return new Employee(ANY_ID, ANY_NAME, annualGrossSalary);
    }
}
