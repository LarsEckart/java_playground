package lars.katas;

import java.util.ArrayList;
import java.util.List;

public class PerfectNumberFinder {

    public static boolean isPerfect(int number) {
        List<Integer> factors = calculateFactors(number);
        int sum = sumOfFactors(factors);
        return sum - number == number;
    }

    private static int sumOfFactors(List<Integer> factors) {
        return factors.stream().mapToInt(i -> i).sum();
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

    /*
    public static boolean isPerfect(int number) {
        List<Integer> factors = new ArrayList<>();
        factors.add(1);
        factors.add(number);
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                factors.add(i);
            }
        }
        int sum = factors.stream().mapToInt(i -> i).sum();
        return sum - number == number;
    }
    */
}
