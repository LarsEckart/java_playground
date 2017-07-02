package ee.tublipoiss.testing.ee.tublipoiss.testing.example;

import org.junit.Test;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class MoneyTest {

    @Test
    public void junit() {
        Money money = new Money("EUR", 41);

        assertEquals(42, money.getAmount());
    }

    @Test
    public void assertj() {
        Money money = new Money("EUR", 41);

        assertThat(money.getAmount()).isEqualTo(42);
    }

    @Test
    public void java8goodness() {
        Money money = new Money(m -> m.amount = 42);
    }

    private class Money {

        private int amount;
        private String currency;

        public Money(String currency, int amount) {
            this.currency = currency;
            this.amount = amount;
        }

        public Money(Consumer<Money> builder) {
            builder.accept(this);
        }

        public int getAmount() {
            return amount;
        }

        public String getCurrency() {
            return currency;
        }
    }
}
