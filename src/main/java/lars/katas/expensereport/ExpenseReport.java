/*
https://github.com/christianhujer/expensereport
 */

package lars.katas.expensereport;

import java.util.Date;
import java.util.List;

enum ExpenseType {
  DINNER,
  BREAKFAST,
  CAR_RENTAL;

  boolean isMeal() {
    return this == DINNER || this == BREAKFAST;
  }

  String typeName() {
    return switch (this) {
      case DINNER -> "Dinner";
      case BREAKFAST -> "Breakfast";
      case CAR_RENTAL -> "Car Rental";
    };
  }
}

class Expense {
  ExpenseType type;
  int amount;

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

      System.out.println(expense.type.typeName() + "\t" + expense.amount + "\t" + mealOverExpensesMarker);
    }

    System.out.println("Meal expenses: " + expenses.calculateMealExpenses());
    System.out.println("Total expenses: " + expenses.total());
  }

}
