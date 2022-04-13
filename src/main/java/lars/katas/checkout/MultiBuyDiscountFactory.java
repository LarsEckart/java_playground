package lars.katas.checkout;

public class MultiBuyDiscountFactory {
  private String watchedSku;
  private Money discount;
  private int trigger;

  public MultiBuyDiscountFactory(String sku, Money discount, int trigger) {
    this.watchedSku = sku;
    this.discount = discount;
    this.trigger = trigger;
  }

  public MultiBuyDiscount create() {
    return new MultiBuyDiscount(watchedSku, discount, trigger);
  }
}
