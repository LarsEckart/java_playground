package lars.katas.bottles;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpeculativelyGeneralBottles extends Bottles {

  private Function<Verse, String> noMore =
      (verse) ->
          "No more bottles of beer on the wall, "
              + "no more bottles of beer.\n"
              + "Go to the store and buy some more, "
              + "99 bottles of beer on the wall.\n";

  private Function<Verse, String> lastOne =
      (verse) ->
          "1 bottle of beer on the wall, "
              + "1 bottle of beer.\n"
              + "Take it down and pass it around, "
              + "no more bottles of beer on the wall.\n";

  private Function<Verse, String> penultimate =
      (verse) ->
          "2 bottles of beer on the wall, "
              + "2 bottles of beer.\n"
              + "Take one down and pass it around, "
              + "1 bottle of beer on the wall.\n";

  private Function<Verse, String> normal =
      (verse) ->
          verse.number
              + " bottles of beer on the wall, "
              + verse.number
              + " bottles of beer.\n"
              + "Take one down and pass it around, "
              + (verse.number - 1)
              + " bottles of beer on the wall.\n";

  @Override
  public String verse(int number) {
    return verseFor(number).text();
  }

  @Override
  public String verses(int start, int end) {
    return Stream.iterate(start, i -> i >= end, i -> i - 1)
        .map(this::verseFor)
        .map(Verse::text)
        .collect(Collectors.joining("\n"));
  }

  private Verse verseFor(int number) {
    switch (number) {
      case 0:
        return new Verse(0, noMore);
      case 1:
        return new Verse(1, lastOne);
      case 2:
        return new Verse(1, penultimate);
      default:
        return new Verse(number, normal);
    }
  }

  class Verse {

    private int number;
    private final Function<Verse, String> lyrics;

    public Verse(int number, Function<Verse, String> lyrics) {
      this.number = number;
      this.lyrics = lyrics;
    }

    public String text() {
      return lyrics.apply(this);
    }
  }
}
