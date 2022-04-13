package lars.katas.checkout;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.larseckart.tcr.FastTestCommitRevertMainExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FastTestCommitRevertMainExtension.class)
public class CheckoutTests {

  @Test
  public void basicPrices() {
    Checkout checkout = new Checkout(50);
    checkout.scan("A");
    assertThat(checkout.currentBalance()).isEqualTo(50);
  }

}
