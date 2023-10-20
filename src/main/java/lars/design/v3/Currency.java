package lars.design.v3;

import java.util.Objects;

class Currency {

  private final String symbol;

  private Currency(String symbol) {
    this.symbol = symbol;
  }

  public static Currency of(String symbol) {
    return new Currency(symbol);
  }

  public String asString() {
    return this.symbol;
  }

  @Override
  public String toString() {
    return this.symbol;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Currency currency = (Currency) o;
    return Objects.equals(symbol, currency.symbol);
  }

  @Override
  public int hashCode() {
    return Objects.hash(symbol);
  }
}
