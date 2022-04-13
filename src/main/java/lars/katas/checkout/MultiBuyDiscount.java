package lars.katas.checkout;

import java.util.Objects;

public final class MultiBuyDiscount {

  private final String item;
  private final Money discount;
  private final int count;
  private int itemsSeen = 0;

  public MultiBuyDiscount(String item, Money discount, int count) {
    this.item = item;
    this.discount = discount;
    this.count = count;
  }

  public Money apply(String item) {
    if (item.equals(this.item)) {
      itemsSeen++;
    }
    if (itemsSeen == count) {
      return discount;
    }
    return Money.ZERO;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
      return false;
    var that = (MultiBuyDiscount) obj;
    return Objects.equals(this.item, that.item) &&
        Objects.equals(this.discount, that.discount) &&
        this.count == that.count;
  }

  @Override
  public int hashCode() {
    return Objects.hash(item, discount, count);
  }

  @Override
  public String toString() {
    return "MultiBuyDiscount[" +
        "item=" + item + ", " +
        "discount=" + discount + ", " +
        "count=" + count + ']';
  }

}
