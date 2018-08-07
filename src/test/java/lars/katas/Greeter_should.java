package lars.katas;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Greeter_should {

    @Test
    public void greet_with_given_name() throws Exception {
        // given
        Greeter greeter = new Greeter();

        // when
        String greeting = greeter.greet("Bob");

        // then
        assertThat(greeting).isEqualTo("Hello, Bob.");
    }

    @Test
    public void greet_with_default_greeting_when_given_null() throws Exception {
        // given
        Greeter greeter = new Greeter();

        // when
        String greeting = greeter.greet(null);

        // then
        assertThat(greeting).isEqualTo("Hello, my friend.");
    }
}
