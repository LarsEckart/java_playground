package lars.vavr;

import io.vavr.collection.Stream;
import io.vavr.control.Try;
import org.junit.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.Fail.fail;

public class Try_should {

    @Test
    public void get_returns_result_when_all_good() throws Exception {
        Try<Integer> parseInt = Try.of(() -> Integer.parseInt("42"));

        Integer result = parseInt.get();

        then(result).isEqualTo(42);
    }

    @Test
    public void get_throws_when_exception_occured() throws Exception {
        Try<Integer> parseInt = Try.of(() -> Integer.parseInt("lars"));

        try {
            parseInt.get();
            fail("should have thrown because 'lars' is not a number");
        } catch (NumberFormatException expected) {
        }
    }

    @Test
    public void getOrElse_returns_else_if_exception_thrown() throws Exception {
        Try<Integer> parseInt = Try.of(() -> Integer.parseInt("lars"));

        Integer result = parseInt.getOrElse(42);

        then(result).isEqualTo(42);
    }

    @Test
    public void getOrElse_returns_result_when_no_exception() throws Exception {
        Try<Integer> parseInt = Try.of(() -> Integer.parseInt("42"));

        Integer result = parseInt.getOrElse(999);

        then(result).isEqualTo(42);
    }
}
