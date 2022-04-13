package lars.katas.checkout;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.larseckart.tcr.FastTestCommitRevertMainExtension;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FastTestCommitRevertMainExtension.class)
public class CheckoutTests {

  @Test
  public void basicPrices() {
    Money priceOfA = randomPrice();
    Money priceOfB = randomPrice();
    Checkout checkout = new Checkout(priceOfA, priceOfB);
    checkout.scan("A");
    checkout.scan("B");
    assertThat(checkout.currentBalance()).isEqualTo(priceOfA.add(priceOfB));
  }

  private Money randomPrice() {
    return Money.fromPence(new Random().nextInt(1000));
  }

}
