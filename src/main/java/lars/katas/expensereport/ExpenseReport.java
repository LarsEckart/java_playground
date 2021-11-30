/*
https://github.com/christianhujer/expensereport
 */

package lars.katas.expensereport;

import java.util.Date;
import java.util.List;

enum ExpenseType {
  DINNER,
  BREAKFAST,
  CAR_RENTAL
}

class Expense {
  ExpenseType type;
  int amount;
}

public class ExpenseReport {
  public void printReport(List<Expense> expenses) {
    int total = 0;
    int mealExpenses = 0;

    System.out.println("Expenses " + new Date());

    for (Expense expense : expenses) {
      if (expense.type == ExpenseType.DINNER || expense.type == ExpenseType.BREAKFAST) {
        mealExpenses += expense.amount;
      }

      String expenseName = switch (expense.type) {
        case DINNER -> "Dinner";
        case BREAKFAST -> "Breakfast";
        case CAR_RENTAL -> "Car Rental";
      };

      String mealOverExpensesMarker =
          expense.type == ExpenseType.DINNER && expense.amount > 5000
                  || expense.type == ExpenseType.BREAKFAST && expense.amount > 1000
              ? "X"
              : " ";

      System.out.println(expenseName + "\t" + expense.amount + "\t" + mealOverExpensesMarker);

      total += expense.amount;
    }

    System.out.println("Meal expenses: " + mealExpenses);
    System.out.println("Total expenses: " + total);
  }
}
