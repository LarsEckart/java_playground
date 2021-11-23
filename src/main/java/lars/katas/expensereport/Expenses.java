package lars.katas.expensereport;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class Expenses implements Iterable<Expense> {

  private final List<Expense> expenses;

  public Expenses(List<Expense> expenses) {
    this.expenses = expenses;
  }

  int total() {
    int total = 0;
    for (Expense expense : expenses) {
      total += expense.amount;
    }
    return total;
  }

  int calculateMealExpenses() {
    int mealExpenses = 0;
    for (Expense expense : expenses) {
      if (expense.type.isMeal()) {
        mealExpenses += expense.amount;
      }
    }
    return mealExpenses;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Expenses) obj;
    return Objects.equals(this.expenses, that.expenses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(expenses);
  }

  @Override
  public String toString() {
    return "Expenses[" + "expenses=" + expenses + ']';
  }

  @Override
  public Iterator<Expense> iterator() {
    return expenses.iterator();
  }
}
