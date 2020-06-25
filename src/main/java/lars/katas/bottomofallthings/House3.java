package lars.katas.bottomofallthings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class House3 {

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
  private List<String> pieces;

  public House3(String order) {
    pieces = switch (order) {
      case "randomized" -> new Random().order(DATA);
      case "mostly_randomized" -> new MostlyRandom().order(DATA);
      default -> new Default().order(DATA);
    };
  }

  private List<String> getPieces() {
    if (pieces == null) {
      pieces = new ArrayList<>(DATA);
    }
    return pieces;
  }

  public String recite() {
    return IntStream.range(1, getPieces().size() + 1)
        .mapToObj(this::line)
        .collect(Collectors.joining(""));
  }

  public String line(int number) {
    return String.format("This is %s.\n", phrase(number));
  }

  private String phrase(int number) {
    return String.join(" ", getPieces().subList(DATA.size() - number, DATA.size()));
  }

  public static void main(String[] args) {
    System.out.println("\n----\n" + new House3("").line(12));
    System.out.println("\n--:random--\n" + new House3("randomized").line(12));
    System.out.println("\n--:mostly_random--\n" + new House3("mostly_randomized").line(12));
  }
}
