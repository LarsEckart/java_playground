package ee.lars.katas.second;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


public class StringCalculatorTest {

    private StringCalculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new StringCalculator();
    }

    @Test
    public void should_return_zero_given_empty_string() throws Exception {
        String empty = "";

        int sum = calculator.add(empty);

        assertThat(sum).isEqualTo(0);
    }

    @Test
    public void should_return_one_when_given_1__in_string() throws Exception {
        String one = "1";

        int sum = calculator.add(one);

        assertThat(sum).isEqualTo(1);
    }

    @Test
    public void should_return_two_when_given_2_in_string() throws Exception {
        String one = "2";

        int sum = calculator.add(one);

        assertThat(sum).isEqualTo(2);
    }

    @Test
    public void should_return_sum_of_two_numbers_separated_by_comma() throws Exception {
        String numbers = "1,2";

        int sum = calculator.add(numbers);

        assertThat(sum).isEqualTo(3);
    }

    @Test
    public void should_return_sum_of_multiple_numbers_separated_by_comma() throws Exception {
        String numbers = "1,2,3";

        int sum = calculator.add(numbers);

        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void should_return_sum_when_some_numbers_separated_by_new_line_and_some_by_comma() throws Exception {
        String numbers = "1\n2,3";

        int sum = calculator.add(numbers);

        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void should_return_sum_when_using_custom_separator() throws Exception {
        String numbers = "//;\n1;2;3";

        int sum = calculator.add(numbers);

        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void should_throw_exception_when_negative_number() throws Exception {
        String numbers = "-1";

        try {
            calculator.add(numbers);
            fail("should have thrown exception because string contains negative number");
        } catch (IllegalArgumentException expected) {

        }
    }

    @Test
    public void show_throw_exception_with_message_containing_all_negative_numbers_when_string_contains_negative_numbers() throws Exception {
        String numbers = "-1,-2,1";

        try {
            calculator.add(numbers);
            fail("should have thrown exception because string contains negative number");
        } catch (IllegalArgumentException expected) {
            assertThat(expected).hasMessage("Input contains negative numbers: [-1, -2]");
        }
    }

    @Test
    public void should_ignore_numbers_larger_than_1000() throws Exception {
        String numbers = "2,1001";

        int sum = calculator.add(numbers);

        assertThat(sum).isEqualTo(2);
    }
}