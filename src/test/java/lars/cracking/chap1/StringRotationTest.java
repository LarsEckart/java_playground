package lars.cracking.chap1;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringRotationTest {

    @Before
    public void initialize() throws Exception {

    }

    @Test
    public void should_return_true_when_word_is_rotation() throws Exception {
        // given
        String original = "waterbottle";
        String rotated = "erbottlewat";

        // when
        final boolean result = StringRotation.isRotation(original, rotated);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void should_return_false_when_word_is_not_rotation() throws Exception {
        // given
        String original = "helloWorld";
        String notRotated = "hollywood";

        // when
        final boolean result = StringRotation.isRotation(original, notRotated);

        // then
        assertThat(result).isFalse();
    }
}
