package ee.lars.advent2017;

import java.util.Arrays;
import java.util.List;

public class ChecksumCalculator {

  public int checksum(String matrix) {
    List<String> rows = Arrays.asList(matrix.split("\n"));

    int sum = 0;
    for (String row : rows) {
      int max = Arrays.stream(row.split("\t")).mapToInt(Integer::parseInt).max().getAsInt();
      int min = Arrays.stream(row.split("\t")).mapToInt(Integer::parseInt).min().getAsInt();
      int diff = max - min;
      sum += diff;
    }
    return sum;
  }

  public int divisableChecksum(String matrix) {
    List<String> rows = Arrays.asList(matrix.split("\n"));

    int sum = 0;
    for (String row : rows) {
      String[] rowItems = row.split("\t");
      for (String item : rowItems) {
        int number = Integer.parseInt(item);

        int index = 0;
        while (index < rowItems.length) {
          int otherNumber = Integer.parseInt(rowItems[index]);
          if (otherNumber != number) {
            int res = number % otherNumber;

            if (res == 0) {
              int diff = number / otherNumber;
              sum += diff;
            }
          }
          index++;
        }
      }
    }
    return sum;
  }
}
