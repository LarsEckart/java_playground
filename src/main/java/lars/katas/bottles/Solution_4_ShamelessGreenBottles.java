package lars.katas.bottles;

import java.util.stream.IntStream;

public class Solution_4_ShamelessGreenBottles implements Bottles {

  @Override
  public String verse(int number) {
    return switch (number) {
      case 0 ->
          "No more bottles of beer on the wall, "
              + "no more bottles of beer.\n"
              + "Go to the store and buy some more, "
              + "99 bottles of beer on the wall.\n";
      case 1 ->
          "1 bottle of beer on the wall, "
              + "1 bottle of beer.\n"
              + "Take it down and pass it around, "
              + "no more bottles of beer on the wall.\n";
      case 2 ->
          "2 bottles of beer on the wall, "
              + "2 bottles of beer.\n"
              + "Take one down and pass it around, "
              + "1 bottle of beer on the wall.\n";
      default ->
          number
              + " bottles of beer on the wall, "
              + number
              + " bottles of beer.\n"
              + "Take one down and pass it around, "
              + (number - 1)
              + " bottles of beer on the wall.\n";
    };
  }

  @Override
  public String verses(int start, int end) {
    return IntStream.iterate(start, i -> i >= end, i -> i - 1)
        .mapToObj(this::verse)
        .reduce((v1, v2) -> v1 + "\n" + v2)
        .orElse("");
  }

  @Override
  public String song() {
    return verses(99, 0);
  }
}
