package ee.lars.threading;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class ParallelSorting {

  public static void main(String[] args) {
    int[] numbers = ThreadLocalRandom.current().ints(50_000_000).toArray();

    for (int i = 0; i < 10; i++) {
      testSorting(numbers);
    }
  }

  private static void testSorting(int[] numbers) {
    int[] numbersSeq = numbers.clone();
    int[] numbersPar = numbers.clone();

    sort("sequential", numbersSeq, Arrays::sort);
    sort("parallel", numbersPar, Arrays::parallelSort);
  }

  private static void sort(String description, int[] numbers, Consumer<int[]> sortAlgorithm) {
    long time = System.currentTimeMillis();
    try {
      sortAlgorithm.accept(numbers);
    } finally {
      time = System.currentTimeMillis() - time;
      System.out.println(description + " time = " + time + "ms");
    }
  }
}
