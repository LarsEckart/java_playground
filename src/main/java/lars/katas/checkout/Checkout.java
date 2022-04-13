package lars.katas.checkout;

public class Checkout {

  private final Money priceOfA;
  private Money priceOfB;
  private MultiBuyDiscount multiBuyDiscount;
  private Money currentBalance = Money.ZERO;
  private int discountForACount;


  public Checkout(Money priceOfA, Money priceOfB, MultiBuyDiscountFactory multiBuyDiscount) {
    this.priceOfA = priceOfA;
    this.priceOfB = priceOfB;
    this.multiBuyDiscount = multiBuyDiscount.create();
  }

  public void scan(String item) {
    if ("A".equals(item)) {
      this.currentBalance = this.currentBalance.add(priceOfA);
    } else {
      this.currentBalance = this.currentBalance.add(priceOfB);
    }
    this.currentBalance = currentBalance.subtract(multiBuyDiscount.apply(item));
  }

  public Money currentBalance() {
    return currentBalance;
  }
}
