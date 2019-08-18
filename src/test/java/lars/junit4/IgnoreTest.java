package lars.junit4;

import org.junit.Ignore;
import org.junit.Test;

public class IgnoreTest {

    @Test
    @Ignore("until issue #42 is resolved")
    public void to_make_tests_or_the_build_pass() {
    }
}
