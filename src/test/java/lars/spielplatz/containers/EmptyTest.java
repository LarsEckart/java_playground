package lars.spielplatz.containers;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmptyTest {


    @Test
    void will_throw_when_checking_contains_null() {
        assertThrows(NullPointerException.class, () -> List.<String>of().contains(null));
    }

    @Test
    void will_NOT_throw_when_checking_contains_null() {
        assertThat(Collections.<String>emptyList().contains(null)).isFalse();
    }

}