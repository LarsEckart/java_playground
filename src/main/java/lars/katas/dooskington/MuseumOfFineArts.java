package lars.katas.dooskington;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.tuple.Tuples;

/*
from: https://github.com/Dooskington/MuseumOfFineArts-RefactoringKata

 * Museum Curator Refactoring Kata
 * It's your first day on the job as museum curator at the Fowler Museum of Fine Arts.
 * Your job involves keeping an eye on the value of all the museums paintings, a value which depends on certain circumstances in the art market
 * and the rarity of each individual painting. Luckily, your predecessor wrote up this C# program to make some solid assumptions for you.
 *
 * You've inputed a few paintings (the ones defined in Main()) but still aren't sure how this tool even works.
 * The code must be refactored!
 *
 * [] Replace Primitive with Object
 * [] Encapsulate Collection
 * [] Replace Temp with Query
 * [] Extract Class
 * [] Substitute Algorithm
 * [] BONUS: Encapsulate Record
 *
 */
public class MuseumOfFineArts {

  public static final class Painting {

    String name;
    String artist;
    double value;
    String rarity;
    Map<String, Pair<String, Double>> transactionLog;
  }


  static class Museum {

    public List<Painting> Paintings = new ArrayList<>();

    // art market conditions
    public boolean IsArtCurrentlyTrending;
    public float ArtMarketSaturation = 1.0f;
    public String CurrentlyPopularRarity = "very_rare";

    public double FinalPaintingValue(Painting p) {
      double rarityModifier = 1.0;
      if ("very_common".equals(p.rarity)) {
        rarityModifier = 0.5;
      } else if ("unique".equals(p.rarity)) {
        rarityModifier = 1.5;
      } else if ("rare".equals(p.rarity)) {
        rarityModifier = 3.0;
      }

      if (CurrentlyPopularRarity.equals(p.rarity)) {
        rarityModifier *= 2.0;
      }

      double artMarketModifier;
      if (!IsArtCurrentlyTrending) {
        artMarketModifier = 0.8;
      } else {
        artMarketModifier = 1.25 - (double) ArtMarketSaturation;
      }

      return p.value * rarityModifier * artMarketModifier;
    }

    public void DisplayPaintingValues() {
      Paintings.forEach(p ->
      {
        double value = FinalPaintingValue(p);
        System.out.printf("%s (%s) is worth %s zorknids and has transfered hands %s times%n",
            p.name, p.rarity, value, p.transactionLog.size());
      });
    }

    public long GetCountOfAnonymousPaintings() {
      return Paintings.stream().filter((p -> p.artist == null || p.artist.isBlank())).count();
    }
  }

  class Program {

    public static void main(String[] args) {
      Museum museum = new Museum();

      Painting abstractPainting = new Painting();
      abstractPainting.name = "Bold and Brash";
      abstractPainting.artist = "Squidward";
      abstractPainting.value = 100.0;
      abstractPainting.transactionLog = Map.of(
          "08/15/1997", Tuples.pair("sold to Bob Binley", 1000.0),
          "08/18/1997", Tuples.pair("sold to Larry Schmelton", 2500.0),
          "02/05/2004", Tuples.pair("sold to museum", 100.0));
      museum.Paintings.add(abstractPainting);

      Painting lostPainting = new Painting();
      lostPainting.name =
          "The Eating of The Cheeseburger";
      lostPainting.value = 275000.0;
      lostPainting.rarity = "rare";
      lostPainting.transactionLog =
          Map.of("06/02/2006", Tuples.pair("sold to museum", 100000.0));

      museum.Paintings.add(lostPainting);

      Painting anotherPainting = new Painting();
      anotherPainting.name = "Day Eagles";
      anotherPainting.artist = "Edward Bopper";
      anotherPainting.value = 500000.0;
      anotherPainting.rarity = "very_rare";
      anotherPainting.transactionLog = Map.of(
          "08/15/1994", Tuples.pair("sold to Jenny Downloadface", 50000.0),
          "02/05/2010", Tuples.pair("sold to museum", 150000.0));

      museum.Paintings.add(anotherPainting);

      museum.DisplayPaintingValues();

      // change some market values and check again
      museum.IsArtCurrentlyTrending = true;
      museum.ArtMarketSaturation = 0.4f;

      museum.DisplayPaintingValues();

      long anonymousCount = museum.GetCountOfAnonymousPaintings();
      System.out.printf("There are %s anonymous paintings in the museum",
          anonymousCount);
    }
  }
}
