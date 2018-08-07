package lars.katas;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Greeter_should {

    private Greeter greeter;

    @Before
    public void setUp() throws Exception {
        greeter = new Greeter();
    }

    @Test
    public void greet_with_given_name() throws Exception {
        // when
        String greeting = greeter.greet("Bob");

        // then
        assertThat(greeting).isEqualTo("Hello, Bob.");
    }

    @Test
    public void greet_with_default_greeting_when_given_null() throws Exception {
        // when
        String greeting = greeter.greet(null);

        // then
        assertThat(greeting).isEqualTo("Hello, my friend.");
    }

    @Test
    public void shout_back_when_name_is_uppercase() throws Exception {
        // when
        String greeting = greeter.greet("JERRY");

        // then
        assertThat(greeting).isEqualTo("HELLO JERRY!");
    }

    @Test
    public void greet_two_people() throws Exception {
        // when
        String greeting = greeter.greet("Jill", "Jane");

        // then
        assertThat(greeting).isEqualTo("Hello, Jill and Jane.");
    }

    @Test
    public void greet_arbitrary_amount_of_people() throws Exception {
        // when
        String greeting = greeter.greet("Amy", "Brian", "Charlotte");

        // then
        assertThat(greeting).isEqualTo("Hello, Amy, Brian and Charlotte.");
    }

    @Test
    public void greet_or_shout_arbitrary_amount_of_people() throws Exception {
        // when
        String greeting = greeter.greet("Amy", "BRIAN", "Charlotte");

        // then
        assertThat(greeting).isEqualTo("Hello, Amy and Charlotte. AND HELLO BRIAN!");
    }


}
