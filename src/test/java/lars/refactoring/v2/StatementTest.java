package lars.refactoring.v2;

import java.util.List;
import java.util.Map;

import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StatementTest {

  private Statement statement = new Statement();

  @Test
  void book_example() {
    List<Performance> performances =
        List.of(
            new Performance("hamlet", 55),
            new Performance("as-like", 35),
            new Performance("othello", 40));

    Map<String, Play> plays =
        Map.of(
            "hamlet", new Play("Hamlet", "tragedy"),
            "as-like", new Play("As You Like It", "comedy"),
            "othello", new Play("Othello", "tragedy"));

    // when
    String statement = this.statement.statement(new Invoice("BigCo", performances), plays);

    assertThat(statement)
        .isEqualTo(
            "Statement for BigCo\n"
                + "  Hamlet: $650.00 (55 seats)\n"
                + "  As You Like It: $580.00 (35 seats)\n"
                + "  Othello: $500.00 (40 seats)\n"
                + "Amount owed is $1,730.00\n"
                + "You earned 47 credits");
  }

  @Test
  void unsupported_type() {
    List<Performance> performances =
        List.of(new Performance("hamlet", 55), new Performance("as-like", 35));

    Map<String, Play> plays =
        Map.of(
            "hamlet", new Play("Hamlet", "tragedy"),
            "as-like", new Play("As You Like It", "pastoral"));

    // when
    assertThrows(
        IllegalArgumentException.class,
        () -> this.statement.statement(new Invoice("BigCo", performances), plays));
  }

  @Test
  void golden_master() throws Exception {
    CombinationApprovals.verifyAllCombinations(
        this::createStatement,
        new String[] {"hamlet", "as-like", "othello"},
        new Integer[] {5, 10, 15, 20, 25, 30, 35, 40, 45});
  }

  private String createStatement(String playId, int audience) {
    List<Performance> performances = List.of(new Performance(playId, audience));
    return statement.statement(new Invoice("BigCo", performances), plays());
  }

  private Map<String, Play> plays() {
    return Map.of(
        "hamlet", new Play("Hamlet", "tragedy"),
        "as-like", new Play("As You Like It", "comedy"),
        "othello", new Play("Othello", "tragedy"));
  }
}
