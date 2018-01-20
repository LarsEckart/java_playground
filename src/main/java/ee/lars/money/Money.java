package ee.lars.money;

abstract class Money {

  protected int amount;

  static Dollar dollar(int amount) {
    return new Dollar(amount);
  }

  static Franc franc(int amount) {
    return new Franc(amount);
  }

  public Money(int amount) {
    this.amount = amount;
  }

  abstract Money times(int amount);

  @Override
  public boolean equals(Object object) {
    Money other = (Money) object;
    return amount == other.amount && getClass().equals(other.getClass());
  }
}
