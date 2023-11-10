package lars.design.v3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

class Money {

  private final Currency currency;
  private final BigDecimal amount;

  public Money(Currency currency, BigDecimal amount) {
    this.currency = Objects.requireNonNull(currency);
    this.amount = Objects.requireNonNull(amount);
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

  @Override
  public boolean equals(Object o) {
    return (o instanceof Money m) && Objects.equals(currency, m.currency)
        && amount.compareTo(m.amount) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(currency, amount);
  }
}
