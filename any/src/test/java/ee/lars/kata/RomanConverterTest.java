package ee.lars.kata;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class RomanConverterTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {1, "I"}, {2, "II"}, {3, "III"}, {4, "IV"}, {5, "V"}, {6, "VI"},
                {7, "VII"}, {8, "VIII"},
        });
    }

    private int input;
    private String expected;

    public RomanConverterTest(int input, String expected) {
        this.input = input;
        this.expected = expected;
    }

    @Test
    public void should_convert_number_to_roman() throws Exception {
        String roman = new RomanConverter().convert(input);

        assertThat(roman).isEqualTo(expected);
    }
}