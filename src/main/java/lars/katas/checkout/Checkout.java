package lars.katas.checkout;

public class Checkout {

  private final Money priceOfA;
  private Money priceOfB;
  private MultiBuyDiscount multiBuyDiscount;
  private Money currentBalance = Money.ZERO;

  public Checkout(Money priceOfA, Money priceOfB, MultiBuyDiscount multiBuyDiscount) {
    this.priceOfA = priceOfA;
    this.priceOfB = priceOfB;
    this.multiBuyDiscount = multiBuyDiscount.reset();
  }

  public void scan(String item) {
    if ("A".equals(item)) {
      this.currentBalance = this.currentBalance.add(priceOfA);
    } else {
      this.currentBalance = this.currentBalance.add(priceOfB);
    }
    this.currentBalance = multiBuyDiscount.apply(currentBalance, item);
  }

  public Money currentBalance() {
    return currentBalance;
  }
}
