package lars.design.v3;

import java.io.IOException;
import java.math.BigDecimal;

class Example {

  public static void main(String[] args) throws IOException, InterruptedException {
    var converter = new CurrencyConverter(new FixerApi());
    var money = new Money(Currency.of("EUR"), BigDecimal.TEN);
    var converted = converter.convert(money, Currency.of("USD"));

    System.out.println(converted);
  }
}
