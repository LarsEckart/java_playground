package lars.junit5;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

@DisplayNameGeneration(ReplaceUnderscores.class)
class MyClass_should {

    @Test
    void do_something_useful() {
        // given
        var words = List.of("Lars", "on", "tubli", "poiss");

        // when
        String sentence = String.join(" ", words);

        // then
        assertThat(sentence).isEqualTo("Lars on tubli poiss");
    }
}
