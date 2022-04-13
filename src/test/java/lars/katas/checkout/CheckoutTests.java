package lars.katas.checkout;

import static org.assertj.core.api.Assertions.*;
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
        new MultiBuyDiscountFactory ("", Money.ZERO, Integer.MAX_VALUE));
    checkout.scan("B");
    checkout.scan("A");
    assertThat(checkout.currentBalance()).isEqualTo(priceOfA.add(priceOfB));
  }

  @Test
  public void discountForTwoAs() {
    Money priceOfA = randomPrice();
    Money discountOfA = Money.fromPence(20);
    MultiBuyDiscountFactory discountFactory = new MultiBuyDiscountFactory("A", discountOfA, 2);
    Checkout checkout = new Checkout(priceOfA, Money.ZERO, discountFactory);
    checkout.scan("A");
    checkout.scan("A");
    assertThat(checkout.currentBalance()).isEqualTo(
        priceOfA.add(priceOfA).subtract(discountOfA));
  }

  @Test
  public void discountForTwoAs2() {
    Money priceOfA = randomPrice();
    Money priceOfB = randomPrice();
    Money discountOfA = Money.fromPence(20);
    MultiBuyDiscountFactory discountFactory = new MultiBuyDiscountFactory("A", discountOfA, 2);
    Checkout checkout = new Checkout(priceOfA, priceOfB, discountFactory);
    checkout.scan("A");
    checkout.scan("B");
    checkout.scan("A");
    assertThat(checkout.currentBalance()).isEqualTo(
        priceOfA.add(priceOfA).add(priceOfB).subtract(discountOfA));
  }

  @Test
  public void independentCheckouts() {
    Money priceOfA = randomPrice();
    MultiBuyDiscountFactory discountFactory = new MultiBuyDiscountFactory("A", Money.fromPence(20), 2);
    Checkout checkout1 = new Checkout(priceOfA, Money.ZERO, discountFactory);
    Checkout checkout2 = new Checkout(priceOfA, Money.ZERO, discountFactory);
    checkout1.scan("A");
    checkout2.scan("A");
    assertThat(checkout1.currentBalance()).isEqualTo(priceOfA);
    assertThat(checkout2.currentBalance()).isEqualTo(priceOfA);
  }

  private Money randomPrice() {
    return Money.fromPence(new Random().nextInt(1000));
  }

}
