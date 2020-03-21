package lars.katas.bottles;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConciseBottles extends Bottles {

  @Override
  public String verse(int n) {

    return (n == 0 ? "No more" : n)
        + (n != 1 ? " bottles " : " bottle ")
        + "of beer on the wall, "
        + (n == 0 ? "no more" : n)
        + (n != 1 ? " bottles " : " bottle ")
        + "of beer."
        + "\n"
        + (n > 0
            ? "Take " + (n > 1 ? "one" : "it") + " down and pass it around, "
            : "Go to the store and buy some more, ")
        + (n - 1 < 0 ? "99" : (n - 1 == 0 ? "no more" : String.valueOf(n - 1)))
        + (n - 1 != 1 ? " bottles " : " bottle ")
        + "of beer on the wall.\n";
  }

  @Override
  public String verses(int start, int end) {
    return Stream.iterate(start, i -> i >= end, i -> i - 1)
        .map(this::verse)
        .collect(Collectors.joining("\n"));
  }
}
