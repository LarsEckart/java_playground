package lars.katas.checkout;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.larseckart.tcr.CommitOnGreenExtension;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(CommitOnGreenExtension.class)
public class CheckoutTests {

  @Test
  public void basicPrices() {
    Money priceOfA = randomPrice();
    Money priceOfB = randomPrice();
    Checkout checkout = new Checkout(priceOfA, priceOfB,
        new MultiBuyDiscount("", Money.ZERO, Integer.MAX_VALUE));
    checkout.scan("B");
    checkout.scan("A");
    assertThat(checkout.currentBalance()).isEqualTo(priceOfA.add(priceOfB));
  }

  @Test
  public void discountForTwoAs() {
    Money priceOfA = randomPrice();
    MultiBuyDiscount multiBuyDiscount = new MultiBuyDiscount("A", Money.fromPence(20), 2);
    Checkout checkout = new Checkout(priceOfA, Money.ZERO, multiBuyDiscount);
    checkout.scan("A");
    checkout.scan("A");
    assertThat(checkout.currentBalance()).isEqualTo(
        priceOfA.add(priceOfA).subtract(multiBuyDiscount.discount()));
  }

  @Test
  public void discountForTwoAs2() {
    Money priceOfA = randomPrice();
    Money priceOfB = randomPrice();
    MultiBuyDiscount multiBuyDiscount = new MultiBuyDiscount("A", Money.fromPence(20), 2);
    Checkout checkout = new Checkout(priceOfA, priceOfB, multiBuyDiscount);
    checkout.scan("A");
    checkout.scan("B");
    checkout.scan("A");
    assertThat(checkout.currentBalance()).isEqualTo(
        priceOfA.add(priceOfA).add(priceOfB).subtract(multiBuyDiscount.discount()));
  }

  private Money randomPrice() {
    return Money.fromPence(new Random().nextInt(1000));
  }

}
