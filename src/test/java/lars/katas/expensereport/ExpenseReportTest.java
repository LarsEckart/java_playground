package lars.katas.expensereport;

import com.github.larseckart.tcr.FastTestCommitRevertMainExtension;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.approvaltests.Approvals;
import org.approvaltests.combinations.CombinationApprovals;
import org.approvaltests.core.Options;
import org.approvaltests.scrubbers.DateScrubber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FastTestCommitRevertMainExtension.class)
class ExpenseReportTest {

  @Test
  void approval() {
    ExpenseType[] expenseType = new ExpenseType[]{ExpenseType.BREAKFAST, ExpenseType.DINNER, ExpenseType.CAR_RENTAL};
    Integer[] amount = new Integer[]{0, 1, 999, 1000, 1001, 4999, 5000, 5001};

    CombinationApprovals.verifyAllCombinations(
        this::createReport, expenseType, amount, new Options(DateScrubber.getScrubberFor("Wed Nov 17 22:28:33 EET 2021")));
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
    ExpenseReport expenseReport = new ExpenseReport();

    Expense expense = new Expense();
    expense.type = expenseType;
    expense.amount = amount;

    expenseReport.printReport(List.of(expense));
  }
}
