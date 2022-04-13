package lars.katas.checkout;

public class Checkout {

  private int itemPrice;

  public Checkout(int itemPrice) {
    this.itemPrice = itemPrice;
  }

  public void scan(String item) {

  }

  public int currentBalance() {
    return itemPrice;
  }
}
