package ee.lars.cracking.chap1;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckPermutationTest {

    private CheckPermutation check;

    @Before
    public void initialize() throws Exception {
        this.check = new CheckPermutation();
    }
    
    @Test
    public void different_length() throws Exception {
        assertThat(this.check.isPermutation("hello", "lars")).isFalse();
    }

    @Test
    public void single_char_words() throws Exception {
        assertThat(this.check.isPermutation("a", "a")).isTrue();
        assertThat(this.check.isPermutation("a", "b")).isFalse();
    }
    
    @Test
    public void multiple_char_words() throws Exception {
        assertThat(this.check.isPermutation("lars", "arsl")).isTrue();
    }
}