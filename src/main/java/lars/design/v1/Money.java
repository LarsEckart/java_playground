package lars.design.v1;

import java.math.BigDecimal;
import java.math.RoundingMode;

class Money {

  private final Currency currency;
  private final BigDecimal amount;

  public Money(Currency currency, BigDecimal amount) {
    this.currency = currency;
    this.amount = amount;
  }

  public Money convert(Currency to, Double rate) {
    BigDecimal newAmount = this.amount.multiply(new BigDecimal(Double.toString(rate)));
    return new Money(to, newAmount);
  }

  public Currency getCurrency() {
    return this.currency;
  }

  @Override
  public String toString() {
    return "Money{" + amount.setScale(2, RoundingMode.CEILING) + " " + currency.asString() + "}";
  }
}
