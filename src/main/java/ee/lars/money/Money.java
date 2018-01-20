package ee.lars.money;

public class Money {

  protected int amount;

  public Money(int amount) {
    this.amount = amount;
  }

  @Override
  public boolean equals(Object object) {
    Money other = (Money) object;
    return amount == other.amount;
  }
}
