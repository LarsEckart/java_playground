package lars.katas.salaryslip;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class SalarySlipGenerator_should {

  private static final String ANY_ID = "12345";
  private static final String ANY_NAME = "John J Doe";
  private static final BigDecimal EXPECTED_MONTHLY_GROSS_SALARY = BigDecimal.valueOf(416.67);

  private SalarySlipGenerator salarySlipGenerator = new SalarySlipGenerator();

  @ParameterizedTest
  @MethodSource("grossSalaryProvider")
  void generate_slip_with_monthly_gross_salary(
      Integer annualGrossSalary, BigDecimal monthlyGrossSalary) {
    // given
    Employee employee = employeeWithAnnualSalaryOf(annualGrossSalary);

    // when
    SalarySlip salarySlip = salarySlipGenerator.generateFor(employee);

    // then
    assertAll(
        () -> assertThat(salarySlip.getEmployeeId()).isEqualTo(ANY_ID),
        () -> assertThat(salarySlip.getEmployeeName()).isEqualTo(ANY_NAME),
        () ->
            assertThat(salarySlip.getMonthlyGrossSalary())
                .isEqualByComparingTo(monthlyGrossSalary));
  }

  static Stream<Arguments> grossSalaryProvider() {
    return Stream.of(
        Arguments.of(5_000, EXPECTED_MONTHLY_GROSS_SALARY),
        Arguments.of(9_060, BigDecimal.valueOf(755.00).setScale(2)),
        Arguments.of(12_000, BigDecimal.valueOf(1_000.00).setScale(2)),
        Arguments.of(101_000, BigDecimal.valueOf(8_416.67).setScale(2)));
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
    assertThat(salarySlip.getNationalInsuranceContributions())
        .isEqualByComparingTo(insuranceContribution);
  }

  static Stream<Arguments> grossSalaryAbove8060() {
    return Stream.of(
        Arguments.of(9_060, BigDecimal.valueOf(10.00).setScale(2)),
        Arguments.of(12_000, BigDecimal.valueOf(39.40).setScale(2)),
        Arguments.of(45_000, BigDecimal.valueOf(352.73).setScale(2)),
        Arguments.of(101_000, BigDecimal.valueOf(446.07).setScale(2)));
  }

  @ParameterizedTest
  @ValueSource(ints = {8060, 5000})
  void generate_slip_with_national_insurance_contributions_zero_when_annual_salary_below_8060(
      int annualSalary) {
    // given
    Employee employee = employeeWithAnnualSalaryOf(annualSalary);

    // when
    SalarySlip salarySlip = salarySlipGenerator.generateFor(employee);

    // then
    assertThat(salarySlip.getNationalInsuranceContributions())
        .isEqualByComparingTo(BigDecimal.ZERO);
  }

  @ParameterizedTest
  @MethodSource("taxNumbers")
  void generate_slip_with_tax_information_when_salary_above_12_000(
      Integer annualSalary, BigDecimal taxableIncome, BigDecimal payableTax) {
    // given
    Employee employee = employeeWithAnnualSalaryOf(annualSalary);

    // when
    SalarySlip salarySlip = salarySlipGenerator.generateFor(employee);

    // then
    assertAll(
        () ->
            assertThat(salarySlip.getTaxFreeAllowance())
                .isEqualByComparingTo(BigDecimal.valueOf(916.67)),
        () -> assertThat(salarySlip.getTaxableIncome()).isEqualByComparingTo(taxableIncome),
        () -> assertThat(salarySlip.getPayableTax()).isEqualByComparingTo(payableTax));
  }

  static Stream<Arguments> taxNumbers() {
    return Stream.of(
        Arguments.of(12_000, BigDecimal.valueOf(83.33), BigDecimal.valueOf(16.67)),
        Arguments.of(45_000, BigDecimal.valueOf(2_833.33), BigDecimal.valueOf(600)));
  }

  @Test
  void generate_slip_with_no_tax_information_when_salary_below_12_000() {
    // given
    Employee employee = employeeWithAnnualSalaryOf(11_000);

    // when
    SalarySlip salarySlip = salarySlipGenerator.generateFor(employee);

    // then
    assertThat(salarySlip.getTaxFreeAllowance()).isEqualByComparingTo(BigDecimal.valueOf(916.67));
    assertThat(salarySlip.getTaxableIncome()).isEqualByComparingTo(BigDecimal.ZERO);
    assertThat(salarySlip.getPayableTax()).isEqualByComparingTo(BigDecimal.ZERO);
  }

  @ParameterizedTest(
      name = "{index}: salary=''{0}'', taxFreeAllowance={1}, taxableIncome={2}, payableTax={3}")
  @MethodSource("highEarners")
  void generate_slip_with_decreased_tax_free_allowance_and_then_higher_payable_tax_for_high_earners(
      Integer annualSalary,
      BigDecimal taxFreeAllowance,
      BigDecimal taxableIncome,
      BigDecimal payableTax) {
    // given
    Employee employee = employeeWithAnnualSalaryOf(annualSalary);

    // when
    SalarySlip salarySlip = salarySlipGenerator.generateFor(employee);

    // then
    assertAll(
        () -> assertThat(salarySlip.getTaxFreeAllowance()).isEqualByComparingTo(taxFreeAllowance),
        () -> assertThat(salarySlip.getTaxableIncome()).isEqualByComparingTo(taxableIncome),
        () -> assertThat(salarySlip.getPayableTax()).isEqualByComparingTo(payableTax));
  }

  static Stream<Arguments> highEarners() {
    return Stream.of(
        Arguments.of(
            101_000,
            BigDecimal.valueOf(875),
            BigDecimal.valueOf(7_541.67),
            BigDecimal.valueOf(2_483.33)),
        Arguments.of(
            111_000,
            BigDecimal.valueOf(458.33),
            BigDecimal.valueOf(8_791.67),
            BigDecimal.valueOf(2_983.33)),
        Arguments.of(
            122_000,
            BigDecimal.valueOf(0.00),
            BigDecimal.valueOf(10_166.67),
            BigDecimal.valueOf(3_533.33)),
        Arguments.of(
            150_000,
            BigDecimal.valueOf(0.00),
            BigDecimal.valueOf(12_500),
            BigDecimal.valueOf(4_466.67)));
  }

  @Test
  void generate_slip_for_super_high_earners() {
    // given
    Employee employee = employeeWithAnnualSalaryOf(160_000);

    // when
    SalarySlip salarySlip = salarySlipGenerator.generateFor(employee);

    // then
    assertAll(
        () ->
            assertThat(salarySlip.getMonthlyGrossSalary())
                .isEqualByComparingTo(BigDecimal.valueOf(13_333.33)),
        () ->
            assertThat(salarySlip.getNationalInsuranceContributions())
                .isEqualByComparingTo(BigDecimal.valueOf(544.40)),
        () -> assertThat(salarySlip.getTaxFreeAllowance()).isEqualByComparingTo(BigDecimal.ZERO),
        () ->
            assertThat(salarySlip.getTaxableIncome())
                .isEqualByComparingTo(BigDecimal.valueOf(13_333.33)),
        () ->
            assertThat(salarySlip.getPayableTax())
                .isEqualByComparingTo(BigDecimal.valueOf(4_841.67)));
  }

  private Employee employeeWithAnnualSalaryOf(Integer annualGrossSalary) {
    return new Employee(ANY_ID, ANY_NAME, BigDecimal.valueOf(annualGrossSalary));
  }
}
