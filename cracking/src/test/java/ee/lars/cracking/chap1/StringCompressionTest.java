package ee.lars.cracking.chap1;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringCompressionTest {

    @Test
    public void returns_original_string_when_compressed_string_is_longer_than_original() throws Exception {
        assertThat(StringCompression.compress("a")).isEqualTo("a");
        assertThat(StringCompression.compress("aa")).isEqualTo("aa");
        assertThat(StringCompression.compress("ab")).isEqualTo("ab");
        assertThat(StringCompression.compress("abc")).isEqualTo("abc");
        assertThat(StringCompression.compress("aabbcc")).isEqualTo("aabbcc");
        assertThat(StringCompression.compress("aaabc")).isEqualTo("aaabc");
        assertThat(StringCompression.compress("aaabbc")).isEqualTo("aaabbc");
    }

    @Test
    public void returns_compressed_string_when_compressed_string_is_shorter_than_original() throws Exception {
        assertThat(StringCompression.compress("aaa")).isEqualTo("a3");
        assertThat(StringCompression.compress("aaabb")).isEqualTo("a3b2");
        assertThat(StringCompression.compress("aaaaabbc")).isEqualTo("a5b2c1");
        assertThat(StringCompression.compress("aabcccccaaa")).isEqualTo("a2b1c5a3");
    }
}