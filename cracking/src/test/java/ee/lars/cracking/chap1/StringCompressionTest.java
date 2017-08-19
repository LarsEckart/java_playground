package ee.lars.cracking.chap1;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringCompressionTest {

    @Test
    public void returns_original_string_for_string_with_length_1() throws Exception {
        // given

        // when
        String compressed = StringCompression.compress("a");

        // then
        assertThat(compressed).isEqualTo("a");
    }
}