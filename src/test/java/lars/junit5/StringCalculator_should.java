package lars.junit5;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class StringCalculator_should {

    @Test
    void return_zero_when_empty_string() {
        int result = StringCalculator.add("");

        assertEquals(result, 0);
    }

    private static class StringCalculator {

        public static int add(String s) {
            return -1;
        }
    }
}
