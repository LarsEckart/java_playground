package ee.lars.money;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MoneyTest {

  @Test
  public void multiplication() throws Exception {
    Dollar five = new Dollar(5);
    Dollar product = five.times(2);
    assertThat(product.amount).isEqualTo(10);
    product = five.times(3);
    assertThat(product.amount).isEqualTo(15);
  }

  @Test
  public void equality() throws Exception {
    assertThat(new Dollar(5)).isEqualTo(new Dollar(5));
  }
}
