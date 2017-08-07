package ee.lars.cracking.chap1;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IsUniqueTest {

    private IsUnique check;

    @Before
    public void initialize() throws Exception {
        this.check = new IsUnique();
    }

    @Test
    public void single_character_string() throws Exception {
        assertThat(this.check.isUnique("a")).isTrue();
        assertThat(this.check.isUnique("b")).isTrue();
        assertThat(this.check.isUnique("C")).isTrue();
    }
    
    @Test
    public void two_character_string() throws Exception {
        assertThat(this.check.isUnique("AA")).isFalse();
        assertThat(this.check.isUnique("BB")).isFalse();
        assertThat(this.check.isUnique("AB")).isTrue();
    }

    @Test
    public void many_character_string() throws Exception {
        assertThat(this.check.isUnique("AAA")).isFalse();
        assertThat(this.check.isUnique("AAB")).isFalse();
        assertThat(this.check.isUnique("ABB")).isFalse();
        assertThat(this.check.isUnique("ABC")).isTrue();
    }
}