package lars.katas.checkout;

public class Checkout {

  private final Money priceOfA;
  private Money priceOfB;
  private Money currentBalance = Money.ZERO;
  private String previousSku = "";
  private Money discountForA;

  public Checkout(Money priceOfA, Money priceOfB, Money discountForA) {
    this.priceOfA = priceOfA;
    this.priceOfB = priceOfB;
    this.discountForA = discountForA;
  }

  public Checkout(Money priceOfA, Money discountForA) {
    this.priceOfA = priceOfA;
    this.discountForA = discountForA;
  }

  public void scan(String item) {
    if ("A".equals(item)) {
      this.currentBalance = this.currentBalance.add(priceOfA);
    } else {
      this.currentBalance = this.currentBalance.add(priceOfB);
    }
    if ("A".equals(previousSku)) {
      this.currentBalance = currentBalance.subtract(discountForA);
    }
    this.previousSku = item;
  }

  public Money currentBalance() {
    return currentBalance;
  }
}
