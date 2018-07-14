package lars.katas;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class RomanConverterTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return List.of(new Object[][] {
                {1, "I"}, {2, "II"}, {3, "III"}, {4, "IV"}, {5, "V"}, {6, "VI"},
                {7, "VII"}, {8, "VIII"}, {9, "IX"}, {10, "X"}, {40, "XL"}, {44, "XLIV"},
                {50, "L"}, {90, "XC"}, {100, "C"}, {400, "CD"}, {500, "D"}, {900, "CM"},
                {1000, "M"}, {846, "DCCCXLVI"}, {1999, "MCMXCIX"}, {2008, "MMVIII"},
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
