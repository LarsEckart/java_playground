package lars.katas;

import java.util.ArrayList;
import java.util.List;

/**
 * In number theory, a perfect number is a positive integer that is equal to the sum of its positive
 * divisors, excluding the number itself. For instance, 6 has divisors 1, 2 and 3 (excluding
 * itself), and 1 + 2 + 3 = 6, so 6 is a perfect number.
 */
public class Number {

  private int value;

  public Number(int value) {
    this.value = value;
  }

  public boolean isPerfect() {
    List<Integer> factors = calculateFactors(value);
    int sum = sumOfFactors(factors);
    return sum - value == value;
  }

  private static List<Integer> calculateFactors(int number) {
    List<Integer> factors = new ArrayList<>();
    factors.add(1);
    factors.add(number);
    for (int i = 2; i <= Math.sqrt(number); i++) {
      if (number % i == 0) {
        factors.add(i);
        factors.add(number / i);
      }
    }
    return factors;
  }

  private static int sumOfFactors(List<Integer> factors) {
    return factors.stream().mapToInt(i -> i).sum();
  }
}
