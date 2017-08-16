package ee.lars.cracking.chap1;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OneWayTest {

    @Before
    public void initialize() throws Exception {

    }

    @Test
    public void should_return_false_if_word_length_differs_by_2() throws Exception {
        // given
        String first = "hello";
        String second = "abc";

        // when
        final boolean oneEditAway = OneWay.oneEditAway(first, second);

        // then
        assertThat(oneEditAway).isFalse();
    }

    @Test
    public void should_return_true_if_palindrome() throws Exception {
        // given
        String first = "hello";
        String second = "leolh";

        // when
        final boolean oneEditAway = OneWay.oneEditAway(first, second);

        // then
        assertThat(oneEditAway).isTrue();
    }

    @Test
    public void should_return_false_if_same_length_but_two_characters_differ() throws Exception {
        // given
        String first = "hello";
        String second = "helab";

        // when
        final boolean oneEditAway = OneWay.oneEditAway(first, second);

        // then
        assertThat(oneEditAway).isFalse();
    }

    @Test
    public void should_return_correct_values_for_book_examples() throws Exception {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(OneWay.oneEditAway("pale", "ple")).isTrue();
            softly.assertThat(OneWay.oneEditAway("pales", "pale")).isTrue();
            softly.assertThat(OneWay.oneEditAway("pale", "bale")).isTrue();
            softly.assertThat(OneWay.oneEditAway("pale", "bake")).isFalse();
        });
    }
}