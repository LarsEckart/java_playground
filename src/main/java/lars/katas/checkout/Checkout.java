package lars.katas.checkout;

public class Checkout {

  private final Money priceOfA;
  private Money priceOfB;
  private MultiBuyDiscount multiBuyDiscount = new MultiBuyDiscount("", Money.ZERO,
      Integer.MAX_VALUE);
  private Money currentBalance = Money.ZERO;
  private int discountForACount;


  public Checkout(Money priceOfA, Money priceOfB, MultiBuyDiscount multiBuyDiscount) {
    this.priceOfA = priceOfA;
    this.priceOfB = priceOfB;
    this.multiBuyDiscount = multiBuyDiscount;
  }

  public void scan(String item) {
    if ("A".equals(item)) {
      this.currentBalance = this.currentBalance.add(priceOfA);
    } else {
      this.currentBalance = this.currentBalance.add(priceOfB);
    }
    if (item.equals(multiBuyDiscount.item())) {
      discountForACount++;
    }
    if (discountForACount == multiBuyDiscount.count()) {
      currentBalance = currentBalance.subtract(multiBuyDiscount.discount());
    }
  }

  public Money currentBalance() {
    return currentBalance;
  }
}
