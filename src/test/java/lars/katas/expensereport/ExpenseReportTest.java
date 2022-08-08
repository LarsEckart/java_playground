package lars.katas.expensereport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import org.approvaltests.combinations.CombinationApprovals;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.Junit5Reporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.scrubbers.DateScrubber;
import org.junit.jupiter.api.Test;

// @ExtendWith(FastTestCommitRevertMainExtension.class)
@UseReporter(Junit5Reporter.class)
class ExpenseReportTest {

  Clock clock = Clock.fixed(Instant.parse("2019-01-01T10:15:30.00Z"), ZoneOffset.UTC);

  @Test
  void approval() {
    ExpenseType[] expenseType = new ExpenseType[]{ExpenseType.BREAKFAST, ExpenseType.DINNER,
        ExpenseType.CAR_RENTAL};
    Integer[] amount = new Integer[]{0, 1, 999, 1000, 1001, 4999, 5000, 5001};

    CombinationApprovals.verifyAllCombinations(
        this::createReport, expenseType, amount,
        new Options());
  }

  private String createReport(ExpenseType expenseType, int amount) {
    final PrintStream systemOut = System.out;
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      System.setOut(new PrintStream(baos, true, StandardCharsets.UTF_8));

      run(expenseType, amount);

      return baos.toString();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      System.setOut(systemOut);
    }
  }

  private void run(ExpenseType expenseType, int amount) {
    ExpenseReport expenseReport = new ExpenseReport(clock);

    Expense expense = new Expense();
    expense.type = expenseType;
    expense.amount = amount;

    expenseReport.printReport(List.of(expense));
  }
}
