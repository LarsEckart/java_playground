package ee.lars.advent2017;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(JUnitParamsRunner.class)
public class SpiralMemory_Should {

  @Parameters({"1, 1",
               "4, 2",
               "16, 4",
               "25, 5",
              })
  @Test
  public void calculate_row_length(int number, int rowLength) throws Exception {
    // given

    // when
    int result = new SpiralMemory().calculateRowLength(number);

    // then
    assertThat(result).isEqualTo(rowLength);
  }
}
