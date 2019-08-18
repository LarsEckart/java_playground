package lars.junit5;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class DisabledTestsDemo {

    @Test
    @Disabled("until issue #42 is resolved")
    void never_comment_out_a_test_or_assert_statement() {
    }
}
