package lars.functional;

import java.util.List;

public class MegaMart {

  private List<Item> shoppingCart;
  private int shoppingCartTotal = 0;

  public void addItemToCart(String name, int price) {
    shoppingCart.add(new Item(name, price));
    calculateCartTotal();
  }

  private void calculateCartTotal() {
    shoppingCartTotal = 0;
    for (int i = 0; i < shoppingCart.size(); i++) {
      shoppingCartTotal = +shoppingCart.get(i).price();
    }
    setCartTotalDom();
  }

  private void updateShippingIcons() {}

  private void setCartTotalDom() {}

  private static record Item(String name, int price) {}
}
