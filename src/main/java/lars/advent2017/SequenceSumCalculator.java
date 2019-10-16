package lars.advent2017;

import java.util.List;
import java.util.stream.Collectors;

public class SequenceSumCalculator {

  public int sum(String captcha) {
    List<Integer> digits = captcha.chars().map(i -> i - 48).boxed().collect(Collectors.toList());
    return sumSequenceJumps(digits, 1);
  }

  public int sumHalf(String captcha) {
    List<Integer> digits = captcha.chars().map(i -> i - 48).boxed().collect(Collectors.toList());
    return sumSequenceJumps(digits, digits.size() / 2);
  }

  private int sumSequenceJumps(List<Integer> digits, int jump) {

    int originalLength = digits.size();
    int i = 0;
    while (i < jump) {
      digits.add(digits.get(i++));
    }
    int sum = 0;
    int index = 0;
    while (index <= originalLength - 1) {
      int current = digits.get(index);
      int compareTo = digits.get(index + jump);

      if (current == compareTo) {
        sum += current;
      }
      index++;
    }
    return sum;
  }
}
