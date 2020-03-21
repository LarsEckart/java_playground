package lars.katas.bottles;

public class ConcretelyAbstractBottles extends Bottles {

  @Override
  public String verse(int bottles) {
    return new Round(bottles).toString();
  }

  private class Round {

    private int bottles;

    public Round(int bottles) {
      this.bottles = bottles;
    }

    @Override
    public String toString() {
      return challenge() + response();
    }

    private String challenge() {
      return capitalize(bottlesOfBeer()) + " " + onWall() + ", " + bottlesOfBeer() + ".\n";
    }

    private String capitalize(String str) {
      return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private String bottlesOfBeer() {
      return anglicizedBottleCount() + " " + pluralizedBottleForm() + " of " + beer();
    }

    private String anglicizedBottleCount() {
      return isAllOut() ? "no more" : String.valueOf(bottles);
    }

    private boolean isAllOut() {
      return bottles == 0;
    }

    private String pluralizedBottleForm() {
      return isLastBeer() ? "bottle" : "bottles";
    }

    private boolean isLastBeer() {
      return bottles == 1;
    }

    private String onWall() {
      return "on the wall";
    }

    private String beer() {
      return "beer";
    }

    private String response() {
      return goToTheStoreOrTakeOneDown() + ", " + bottlesOfBeer() + " " + onWall() + ".\n";
    }

    private String goToTheStoreOrTakeOneDown() {
      if (isAllOut()) {
        bottles = 99;
        return buyNewBeer();
      } else {
        var lyrics = drinkBeer();
        bottles -= 1;
        return lyrics;
      }
    }

    private String buyNewBeer() {
      return "Go to the store and buy some more";
    }

    private String drinkBeer() {
      return "Take " + itOrOne() + " down and pass it around";
    }

    private String itOrOne() {
      return isLastBeer() ? "it" : "one";
    }
  }
}
