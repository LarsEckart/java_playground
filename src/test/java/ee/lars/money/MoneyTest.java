package ee.lars.money;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MoneyTest {

  @Test
  public void multiplication() throws Exception {
    Money five = Money.dollar(5);
    assertThat(five.times(2)).isEqualTo(Money.dollar(10));
    assertThat(five.times(3)).isEqualTo(Money.dollar(15));

    Money fiveFranc = Money.franc(5);
    assertThat(fiveFranc.times(2)).isEqualTo(Money.franc(10));
    assertThat(fiveFranc.times(3)).isEqualTo(Money.franc(15));
  }

  @Test
  public void equality() throws Exception {
    assertThat(Money.dollar(5)).isEqualTo(Money.dollar(5));

    assertThat(Money.franc(5)).isEqualTo(Money.franc(5));

    assertThat(Money.franc(5)).isNotEqualTo(Money.dollar(5));
  }
}
