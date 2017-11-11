package ee.lars.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonTest {

    @Test
    public void test_auto_value() throws Exception {
        // given
        Person p = new Person("Lars", 42);

        // when
        assertThat(p.getName()).isEqualTo("Lars");

        // then
    }
}
