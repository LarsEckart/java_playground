package lars.katas.checkout;

public class Checkout {

  private final Money priceOfA;
  private final Money priceOfB;
  private Money currentBalance = Money.ZERO;

  public Checkout(Money priceOfA, Money priceOfB) {
    this.priceOfA = priceOfA;
    this.priceOfB = priceOfB;
  }

  public void scan(String item) {
    if ("A".equals(item)) {
      this.currentBalance = this.currentBalance.add(priceOfA);
    } else {
      this.currentBalance = this.currentBalance.add(priceOfB);
    }
  }

  public Money currentBalance() {
    return currentBalance;
  }
}
