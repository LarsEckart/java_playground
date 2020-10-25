package lars.design.v3;

class CurrencyConverter {

  private final ExchangeRates exchangeRates;

  CurrencyConverter(ExchangeRates exchangeRates) {
    this.exchangeRates = exchangeRates;
  }

  public Money convert(Money money, Currency to) {
    var exchangeRate = exchangeRates.exchangeRateFor(money.getCurrency(), to);

    return money.convert(exchangeRate);
  }
}
