package lars.cracking.chap1;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleStringCompressorTest {

    @Test
    public void returns_original_string_when_compressed_string_is_longer_than_original() throws Exception {
        assertThat(SimpleStringCompressor.compress("a")).isEqualTo("a");
        assertThat(SimpleStringCompressor.compress("aa")).isEqualTo("aa");
        assertThat(SimpleStringCompressor.compress("ab")).isEqualTo("ab");
        assertThat(SimpleStringCompressor.compress("abc")).isEqualTo("abc");
        assertThat(SimpleStringCompressor.compress("aabbcc")).isEqualTo("aabbcc");
        assertThat(SimpleStringCompressor.compress("aaabc")).isEqualTo("aaabc");
        assertThat(SimpleStringCompressor.compress("aaabbc")).isEqualTo("aaabbc");
    }

    @Test
    public void returns_compressed_string_when_compressed_string_is_shorter_than_original() throws Exception {
        assertThat(SimpleStringCompressor.compress("aaa")).isEqualTo("a3");
        assertThat(SimpleStringCompressor.compress("aaabb")).isEqualTo("a3b2");
        assertThat(SimpleStringCompressor.compress("aaaaabbc")).isEqualTo("a5b2c1");
        assertThat(SimpleStringCompressor.compress("aabcccccaaa")).isEqualTo("a2b1c5a3");
    }
}
