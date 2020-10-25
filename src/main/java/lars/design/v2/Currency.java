package lars.design.v2;

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
}
