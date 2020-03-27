package lars.katas;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class AcronymTest {

    @Test
    public void basic() {
        String phrase = "Portable Network Graphics";
        String expected = "PNG";
        assertThat(new Acronym(phrase).get()).isEqualTo(expected);
    }

}
