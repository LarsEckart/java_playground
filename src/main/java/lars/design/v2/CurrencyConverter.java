package lars.design.v2;

import java.io.IOException;

class CurrencyConverter {

  private FixerApi fixerApi;

  CurrencyConverter(FixerApi fixerApi) {
    this.fixerApi = fixerApi;
  }

  public Money convert(Money money, Currency to) throws IOException, InterruptedException {
    ExchangeRate exchangeRate = fixerApi.exchangeRateFor(money.getCurrency(), to);

    return money.convert(exchangeRate);
  }
}
