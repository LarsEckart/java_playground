package ee.lars.money;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MoneyTest {

  @Test
  public void multiplication() throws Exception {
    Dollar five = new Dollar(5);
    assertThat(five.times(2)).isEqualTo(new Dollar(10));
    assertThat(five.times(3)).isEqualTo(new Dollar(15));
  }

  @Test
  public void equality() throws Exception {
    assertThat(new Dollar(5)).isEqualTo(new Dollar(5));
  }

  @Test
  public void multiplication_franc() throws Exception {
    Franc five = new Franc(5);
    assertThat(five.times(2)).isEqualTo(new Franc(10));
    assertThat(five.times(3)).isEqualTo(new Franc(15));
  }

  @Test
  public void equality_franc() throws Exception {
    assertThat(new Franc(5)).isEqualTo(new Franc(5));
  }
}
