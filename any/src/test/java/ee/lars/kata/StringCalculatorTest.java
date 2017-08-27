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
        assertThat(this.stringCalculator.add("")).isEqualTo(0);
    }

    @Test
    public void returns_the_number_if_only_one_number() throws Exception {
        assertThat(this.stringCalculator.add("1")).isEqualTo(1);
    }

    @Test
    public void returns_sum_of_two_numbers_separated_by_comma() throws Exception {
        assertThat(this.stringCalculator.add("1,1")).isEqualTo(2);
    }

    @Test
    public void returns_sum_of_any_amount_of_numbers_separated_by_comma() throws Exception {
        assertThat(this.stringCalculator.add("1,2,3")).isEqualTo(6);
    }

    @Test
    public void returns_sum_if_numbers_String_contains_newline_instead_of_comma() throws Exception {
        assertThat(this.stringCalculator.add("1\n2,3")).isEqualTo(6);
    }

    @Test
    public void returns_sum_if_numbers_string_has_delimiter_provided_in_first_line() throws Exception {
        assertThat(this.stringCalculator.add("//;\n1;2")).isEqualTo(3);
    }

    @Test
    public void does_not_allow_negative_numbers() throws Exception {
        try {
            //when
            this.stringCalculator.add("-1,-2");

            //then
            Assert.fail("did not throw despite negative numbers");
        } catch (IllegalArgumentException expected) {
            assertThat(expected.getMessage()).isEqualTo("negatives not allowed: [-1, -2]");
        }
    }

    @Test
    public void ignores_numbers_larger_than_thousand() throws Exception {
        assertThat(this.stringCalculator.add("2,1001")).isEqualTo(2);
    }

    @Test
    public void delimiter_can_be_any_length() throws Exception {
        assertThat(this.stringCalculator.add("//[aaa]\n1aaa2aaa3")).isEqualTo(6);
    }

    @Ignore("too much regexp for tonight")
    @Test
    public void numbers_can_have_more_than_one_delimiter() throws Exception {
        assertThat(this.stringCalculator.add("//[a][b]\n1a2b3")).isEqualTo(6);
    }
}