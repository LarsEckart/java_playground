package ee.lars.money;

class Money implements Expression {

  protected int amount;
  protected String currency;

  public Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  static Money dollar(int amount) {
    return new Money(amount, "USD");
  }

  static Money franc(int amount) {
    return new Money(amount, "CHF");
  }

  String currency() {
    return currency;
  }

  Money times(int multiplier) {
    return new Money(amount * multiplier, currency);
  }

  @Override public String toString() {
    return "Money{" +
        "amount=" + amount +
        ", currency='" + currency + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object object) {
    Money other = (Money) object;
    return amount == other.amount && currency.equals(other.currency);
  }

  public Expression plus(Money other) {
    return new Money(amount + other.amount, currency);
  }
}
