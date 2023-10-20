package lars.advent2017;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class SequenceSumCalculator_Should {

  @Test
  public void calculate_sum_of_digits_that_are_equal_to_next_digit_in_sequence() throws Exception {
    SequenceSumCalculator calculator = new SequenceSumCalculator();
    int sum = calculator.sum("1122");
    assertThat(sum).isEqualTo(3);
  }

  @Test
  public void
      calculate_sum_of_digits_that_are_equal_to_digit_that_is_half_the_sequence_length_away()
          throws Exception {
    SequenceSumCalculator calculator = new SequenceSumCalculator();
    int sum = calculator.sumHalf("1212");
    assertThat(sum).isEqualTo(6);
  }
}
