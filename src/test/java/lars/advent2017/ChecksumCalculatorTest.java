package lars.advent2017;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChecksumCalculatorTest {

    private StringBuilder strb;

    @Before
    public void setUp() throws Exception {
        strb = new StringBuilder();
    }

    @Test
    public void should_do_this() throws Exception {
        // given
        String input = "hello";
        String otherInput = "world";

        // when
        strb.append(input);
        strb.append(otherInput);
        String result = strb.toString();

        // then
        assertThat(result).startsWith("hello");
    }
}
