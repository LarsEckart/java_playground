package lars.junit5;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class StringCalculator_should {

  @Disabled("demo of test with switched assertEquals argument order")
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
