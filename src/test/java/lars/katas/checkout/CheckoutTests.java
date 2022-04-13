package lars.katas.checkout;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.larseckart.tcr.FastTestCommitRevertMainExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FastTestCommitRevertMainExtension.class)
public class CheckoutTests {

  @Test
  public void basicPrices() {
    Money priceOfA = randomPrice();
    Checkout checkout = new Checkout(priceOfA);
    checkout.scan("A");
    assertThat(checkout.currentBalance()).isEqualTo(priceOfA);
  }

  private Money randomPrice() {
    return new Money(50);
  }

}
