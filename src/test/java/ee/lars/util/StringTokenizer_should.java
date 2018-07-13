package ee.lars.util;

import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringTokenizer_should {

    @Test
    public void do_something() throws Exception {
        StringTokenizer st = new StringTokenizer("hello world   olen tubli poiss\njawohl");

        assertThat(st.countTokens()).isEqualTo(6);
    }

    @Test
    public void name() throws Exception {
        StringTokenizer st = new StringTokenizer("hello world   olen tubli poiss\njawohl");

        List<Object> tokens = Collections.list(st);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(tokens).contains("hello");
            softly.assertThat(tokens).contains("world");
            softly.assertThat(tokens).contains("olen");
            softly.assertThat(tokens).contains("tubli");
            softly.assertThat(tokens).contains("poiss");
            softly.assertThat(tokens).contains("jawohl");
        });
    }
}
