package lars.vavr;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.Fail.fail;

public class Try_should {

    @Test
    void get_returns_result_when_all_good() throws Exception {
        Try<Integer> parseInt = Try.of(() -> Integer.parseInt("42"));

        then(parseInt.get()).isEqualTo(42);
    }

    @Test
    void get_throws_when_exception_occured() throws Exception {
        Try<Integer> parseInt = Try.of(() -> Integer.parseInt("lars"));

        try {
            parseInt.get();
            fail("should have thrown because 'lars' is not a number");
        } catch (NumberFormatException expected) {
        }
    }

    @Test
    void getOrElse_returns_else_if_exception_thrown() throws Exception {
        Try<Integer> parseInt = Try.of(() -> Integer.parseInt("lars"));

        then(parseInt.getOrElse(42)).isEqualTo(42);
    }

    @Test
    void getOrElse_returns_result_when_no_exception() throws Exception {
        Try<Integer> parseInt = Try.of(() -> Integer.parseInt("42"));

        then(parseInt.getOrElse(999)).isEqualTo(42);
    }
}
