package de.larseckart.spielplatz.j8ia;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TradingTest {



    @Test
    public void should_solve_all_stream_challenges() throws Exception {
        // given
        final Trader raoul = new Trader("Raoul", "Cambridge");
        final Trader mario = new Trader("Mario", "Milan");
        final Trader alan = new Trader("Alan", "Cambridge");
        final Trader brian = new Trader("Brian", "Cambridge");
        final Transaction transaction = new Transaction(brian, 2011, 300);
        final Transaction transaction1 = new Transaction(raoul, 2012, 1000);
        final Transaction transaction2 = new Transaction(raoul, 2011, 400);
        final Transaction transaction3 = new Transaction(mario, 2012, 710);
        final Transaction transaction4 = new Transaction(mario, 2012, 700);
        final Transaction transaction5 = new Transaction(alan, 2012, 950);
        final List<Transaction> transactions = Arrays.asList(
                transaction,
                transaction1,
                transaction2,
                transaction3,
                transaction4,
                transaction5
        );

        final Trading trading = new Trading();

        // when
        final List<Transaction> transactions2011 = trading.findAllTransactionsSorted(transactions, 2011);
        final List<String> cities = trading.uniqueCities(transactions);
        final List<String> tradersFromCambridge = trading.tradersFrom(transactions, "Cambridge");
        final String traderNames = trading.stringOfAllTradersNamesSortedAlphabetically(transactions);
        final boolean anyTraderFromMilan = trading.anyTraderFrom(transactions, "Milan");
        final Optional<Integer> highestTransactionValue = trading.highestTransactionValue(transactions);
        final Optional<Transaction> smallestTransaction = trading.smallestTransaction(transactions);

        // then
        assertThat(transactions2011).containsExactly(transaction, transaction2);
        assertThat(cities).containsExactlyInAnyOrder("Cambridge", "Milan");
        assertThat(tradersFromCambridge).containsExactly("Alan", "Brian", "Raoul");
        assertThat(traderNames).isEqualTo("AlanBrianMarioRaoul");
        assertThat(anyTraderFromMilan).isTrue();
        assertThat(highestTransactionValue.get()).isEqualTo(1000);
        assertThat(smallestTransaction.get()).isEqualTo(transaction);
    }
}