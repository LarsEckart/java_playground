package ee.lars.money;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MoneyTest {

  @Test
  public void multiplication() throws Exception {
    Dollar five = new Dollar(5);
    five.times(2);
    assertThat(five.amount).isEqualTo(10);
  }

  class Dollar {

    int amount;

    public Dollar(int amount) {
      this.amount = amount;
    }

    void times(int multiplier) {
      amount *= multiplier;
    }
  }
}
