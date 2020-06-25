package lars.katas.bottomofallthings;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class House {

  private List<String> DATA =
      List.of(
          "the horse and the hound and the horn that belonged to",
          "the farmer sowing his corn that kept",
          "the rooster that crowed in the morn that woke",
          "the priest all shaven and shorn that married",
          "the man all tattered and torn that kissed",
          "the maiden all forlorn that milked",
          "the cow with the crumpled horn that tossed",
          "the dog that worried",
          "the cat that killed",
          "the rat that ate",
          "the malt that lay in",
          "the house that Jack built");

  public String recite() {
    return IntStream.range(1, DATA.size() + 1).mapToObj(this::line).collect(Collectors.joining(""));
  }

  public String line(int number) {
    return String.format("This is %s.\n", phrase(number));
  }

  private String phrase(int number) {
    return String.join(" ", DATA.subList(DATA.size() - number, DATA.size()));
  }

  public static void main(String[] args) {
    System.out.println("\n----\n");
    System.out.println(new House().line(12));
  }
}
