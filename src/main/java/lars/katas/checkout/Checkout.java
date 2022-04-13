package lars.katas.checkout;

public class Checkout {

  private final Money priceOfA;
  private Money priceOfB;
  private Money currentBalance = Money.ZERO;
  private String previousSku = "";

  public Checkout(Money priceOfA, Money priceOfB) {
    this.priceOfA = priceOfA;
    this.priceOfB = priceOfB;
  }

  public Checkout(Money priceOfA) {
    this.priceOfA = priceOfA;
  }

  public void scan(String item) {
    if ("A".equals(item)) {
      this.currentBalance = this.currentBalance.add(priceOfA);
    } else {
      this.currentBalance = this.currentBalance.add(priceOfB);
    }
    if ("A".equals(previousSku)) {
      this.currentBalance = currentBalance.subtract(Money.fromPence(20));
    }
    this.previousSku = item;
  }

  public Money currentBalance() {
    return currentBalance;
  }
}
