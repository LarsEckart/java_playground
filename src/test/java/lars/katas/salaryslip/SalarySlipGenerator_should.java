package lars.katas.salaryslip;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

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
    void generate_slip_with_monthly_gross_salary(Integer annualGrossSalary, BigDecimal monthlyGrossSalary) {
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
            Arguments.of(5_000, EXPECTED_MONTHLY_GROSS_SALARY),
            Arguments.of(9_060, BigDecimal.valueOf(755.00).setScale(2))
        );
    }

    @ParameterizedTest
    @MethodSource("grossSalaryAbove8060")
    void generate_slip_with_national_insurance_contributions_when_annual_salary_above_8060(
        Integer annualGrossSalary, BigDecimal insuranceContribution) {
        // given
        Employee employee = employeeWithAnnualSalaryOf(annualGrossSalary);

        // when
        SalarySlip salarySlip = salarySlipGenerator.generateFor(employee);

        // then
        assertThat(salarySlip.getNationalInsuranceContributions()).isEqualTo(insuranceContribution);
    }

    static Stream<Arguments> grossSalaryAbove8060() {
        return Stream.of(
            Arguments.of(9_060, BigDecimal.valueOf(10.00).setScale(2)),
            Arguments.of(12_000, BigDecimal.valueOf(39.40).setScale(2))
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {8060, 5000})
    void generate_slip_with_national_insurance_contributions_zero_when_annual_salary_below_8060(int annualSalary) {
        // given
        Employee employee = employeeWithAnnualSalaryOf(annualSalary);

        // when
        SalarySlip salarySlip = salarySlipGenerator.generateFor(employee);

        // then
        assertThat(salarySlip.getNationalInsuranceContributions()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void generate_slip_with_tax_information_when_salary_above_12_000() {
        // given

        // when

        // then

    }

    private Employee employeeWithAnnualSalaryOf(Integer annualGrossSalary) {
        return new Employee(ANY_ID, ANY_NAME, BigDecimal.valueOf(annualGrossSalary));
    }
}
