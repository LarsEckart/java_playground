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

  boolean isMeal() {
    return type == ExpenseType.DINNER || type == ExpenseType.BREAKFAST;
  }

  String name() {
    return switch (type) {
      case DINNER -> "Dinner";
      case BREAKFAST -> "Breakfast";
      case CAR_RENTAL -> "Car Rental";
    };
  }

  boolean aboveLimit() {
    return type == ExpenseType.DINNER && amount > 5000
            || type == ExpenseType.BREAKFAST && amount > 1000;
  }
}

public class ExpenseReport {
  public void printReport(List<Expense> expenseList) {
    Expenses expenses = new Expenses(expenseList);

    System.out.println("Expenses " + new Date());

    for (Expense expense : expenses) {
      String mealOverExpensesMarker =
          expense.aboveLimit()
              ? "X"
              : " ";

      System.out.println(expense.name() + "\t" + expense.amount + "\t" + mealOverExpensesMarker);
    }

    System.out.println("Meal expenses: " + expenses.calculateMealExpenses());
    System.out.println("Total expenses: " + expenses.total());
  }

}
