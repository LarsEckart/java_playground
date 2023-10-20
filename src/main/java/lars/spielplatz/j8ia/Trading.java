package lars.spielplatz.j8ia;

import static java.util.Comparator.comparing;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Trading {

  public List<Transaction> findAllTransactionsSorted(List<Transaction> transactions, int year) {
    return transactions.stream()
        .filter(transaction -> transaction.getYear() == year)
        .sorted(comparing(Transaction::getValue))
        .collect(Collectors.toList());
  }

  public List<String> uniqueCities(List<Transaction> transactions) {
    return transactions.stream()
        .map(transaction -> transaction.getTrader().getCity())
        .distinct()
        .collect(Collectors.toList());
  }

  public List<String> tradersFrom(List<Transaction> transactions, String city) {
    return transactions.stream()
        .filter(transaction -> transaction.getTrader().getCity().equals(city))
        .map(transaction -> transaction.getTrader().getName())
        .distinct()
        .sorted()
        .collect(Collectors.toList());
  }

  public String stringOfAllTradersNamesSortedAlphabetically(List<Transaction> transactions) {
    return transactions.stream()
        .map(transaction -> transaction.getTrader().getName())
        .distinct()
        .sorted()
        .reduce("", (name1, name2) -> name1 + name2);
  }

  public boolean anyTraderFrom(List<Transaction> transactions, String city) {
    return transactions.stream()
        .anyMatch(transaction -> city.equals(transaction.getTrader().getCity()));
  }

  public Optional<Integer> highestTransactionValue(List<Transaction> transactions) {
    return transactions.stream().map(transaction -> transaction.getValue()).reduce(Integer::max);
  }

  public Optional<Transaction> smallestTransaction(List<Transaction> transactions) {
    return transactions.stream().min(Comparator.comparing(Transaction::getValue));
  }
}
