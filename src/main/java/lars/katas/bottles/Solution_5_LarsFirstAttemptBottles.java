package lars.katas.bottles;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution_5_LarsFirstAttemptBottles implements Bottles {

  public String verse(int n) {
    if (n == 0) {
      return "No more bottles of beer on the wall, no more bottles of beer.\n"
          + "Go to the store and buy some more, 99 bottles of beer on the wall.\n";
    }

    String result = "";

    String bottles = n == 1 ? "bottle" : "bottles";
    result += String.format("%d %s of beer on the wall, %d %s of beer.\n", n, bottles, n, bottles);

    int afterBottleCount = n - 1;
    String afterBottles = n - 1 == 1 ? "bottle" : "bottles";

    if (afterBottleCount == 0) {
      result += "Take it down and pass it around, ";
      result += "no more bottles of beer on the wall.\n";
    } else {
      result += "Take one down and pass it around, ";
      result += String.format("%d %s of beer on the wall.\n", afterBottleCount, afterBottles);
    }
    return result;
  }

  public String verses(int start, int end) {
    return Stream.iterate(start, i -> i >= end, i -> i - 1)
        .map(this::verse)
        .collect(Collectors.joining("\n"));
  }

  public String song() {
    return verses(99, 0);
  }
}
