package lars.advent2017;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ChecksumCalculator_Should {

  @Test
  public void do_stuff() throws Exception {
    // given
    ChecksumCalculator calculator = new ChecksumCalculator();

    // when
    int checksum = calculator.checksum("5\t1\t9\t5\n" + "7\t5\t3\n" + "2\t4\t6\t8");

    // then
    assertThat(checksum).isEqualTo(18);
  }

  @Test
  public void do_stuff_when_divisible() throws Exception {
    // given
    ChecksumCalculator calculator = new ChecksumCalculator();

    // when
    int checksum = calculator.divisableChecksum("5\t9\t2\t8\n" + "9\t4\t7\t3\n" + "3\t8\t6\t5");

    // then
    assertThat(checksum).isEqualTo(9);
  }
}
