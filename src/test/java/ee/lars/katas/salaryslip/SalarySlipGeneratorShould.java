package ee.lars.katas.salaryslip;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SalarySlipGeneratorShould {

    private SalarySlipGenerator salarySlipGenerator;

    @Before
    public void setUp() throws Exception {
        salarySlipGenerator = new SalarySlipGenerator();
    }

    @Test
    public void generate_salary_slip_for_anual_income_of_5000() throws Exception {
        // given
        SalarySlip expected = new SalarySlip(12345, "John Doe", BigDecimal.valueOf(416.67));
        Employee employee = new Employee(12345, "John Doe", BigDecimal.valueOf(5000L));

        // when
        SalarySlip actual = salarySlipGenerator.generateFor(employee);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
