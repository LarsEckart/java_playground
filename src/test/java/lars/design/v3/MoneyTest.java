package lars.design.v3;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
class MoneyTest {

  @Test
  void test_currency_conversion() {
    var converter = new CurrencyConverter((from, to) -> new ExchangeRate(from, to, 2.0));

    Money converted =
        converter.convert(new Money(Currency.of("EUR"), BigDecimal.TEN), Currency.of("AUD"));

    assertThat(converted).isEqualTo(new Money(Currency.of("AUD"), new BigDecimal("20")));
  }

  @Disabled("not working after latest equalsverifier update")
  @Test
  void equalsVerified() {
    EqualsVerifier.simple().forClass(Money.class).withNonnullFields("amount").verify();
  }
}
