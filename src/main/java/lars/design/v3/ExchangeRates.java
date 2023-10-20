package lars.design.v3;

public interface ExchangeRates {

  ExchangeRate exchangeRateFor(Currency from, Currency to);
}
