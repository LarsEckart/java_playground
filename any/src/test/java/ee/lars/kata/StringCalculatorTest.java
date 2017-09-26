package ee.lars.kata;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringCalculatorTest {

    private StringCalculator stringCalculator;

    @Before
    public void initialize() throws Exception {
        this.stringCalculator = new StringCalculator();
    }

    @Test
    public void returns_zero_for_empty_string() throws Exception {
        final String input = "";
        final int sum = this.stringCalculator.add(input);
        assertThat(sum).isEqualTo(0);
    }

    @Test
    public void returns_the_number_if_only_one_number() throws Exception {
        final String input = "1";
        final int sum = this.stringCalculator.add(input);
        assertThat(sum).isEqualTo(1);
    }

    @Test
    public void returns_sum_of_two_numbers_separated_by_comma() throws Exception {
        final String input = "1,1";
        final int sum = this.stringCalculator.add(input);
        assertThat(sum).isEqualTo(2);
    }

    @Test
    public void returns_sum_of_any_amount_of_numbers_separated_by_comma() throws Exception {
        final String input = "1,2,3";
        final int sum = this.stringCalculator.add(input);
        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void returns_sum_if_numbers_String_contains_newline_instead_of_comma() throws Exception {
        final String input = "1\n2,3";
        final int sum = this.stringCalculator.add(input);
        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void returns_sum_if_numbers_string_has_delimiter_provided_in_first_line() throws Exception {
        final String input = "//;\n1;2";
        final int sum = this.stringCalculator.add(input);
        assertThat(sum).isEqualTo(3);
    }

    @Test
    public void does_not_allow_negative_numbers() throws Exception {
        final String input = "-1,-2";
        try {
            //when
            this.stringCalculator.add(input);

            //then
            Assert.fail("did not throw despite negative numbers");
        } catch (IllegalArgumentException expected) {
            assertThat(expected.getMessage()).isEqualTo("negatives not allowed: [-1, -2]");
        }
    }

    @Test
    public void ignores_numbers_larger_than_thousand() throws Exception {
        final String input = "2,1001";
        final int sum = this.stringCalculator.add(input);
        assertThat(sum).isEqualTo(2);
    }

    @Test
    public void delimiter_can_be_any_length() throws Exception {
        final String input = "//[aaa]\n1aaa2aaa3";
        final int sum = this.stringCalculator.add(input);
        assertThat(sum).isEqualTo(6);
    }

    @Ignore("too much regexp for tonight")
    @Test
    public void numbers_can_have_more_than_one_delimiter() throws Exception {
        final int sum = this.stringCalculator.add("//[a][b]\n1a2b3");
        assertThat(sum).isEqualTo(6);
    }
}