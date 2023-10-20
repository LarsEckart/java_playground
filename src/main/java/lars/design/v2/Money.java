package lars.design.v2;

import java.math.BigDecimal;
import java.math.RoundingMode;

class Money {

  private final Currency currency;
  private final BigDecimal amount;

  public Money(Currency currency, BigDecimal amount) {
    this.currency = currency;
    this.amount = amount;
  }

  public Money convert(ExchangeRate exchangeRate) {
    BigDecimal newAmount =
        this.amount.multiply(new BigDecimal(Double.toString(exchangeRate.rate())));
    return new Money(exchangeRate.to(), newAmount);
  }

  public Currency getCurrency() {
    return this.currency;
  }

  @Override
  public String toString() {
    return "Money{" + amount.setScale(2, RoundingMode.CEILING) + " " + currency.asString() + "}";
  }
}
