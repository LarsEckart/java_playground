package lars.katas.checkout;

public class Checkout {

  private Money itemPrice;

  public Checkout(Money money) {
    this.itemPrice = money;
  }

  public void scan(String item) {

  }

  public Money currentBalance() {
    return itemPrice;
  }
}
