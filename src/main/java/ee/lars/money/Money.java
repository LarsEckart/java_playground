package ee.lars.money;

abstract class Money {

  protected int amount;
  protected String currency;

  public Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  static Dollar dollar(int amount) {
    return new Dollar(amount, "USD");
  }

  static Franc franc(int amount) {
    return new Franc(amount, "CHF");
  }

  abstract Money times(int amount);

  @Override
  public boolean equals(Object object) {
    Money other = (Money) object;
    return amount == other.amount && getClass().equals(other.getClass());
  }

  String currency() {
    return currency;
  }
}
