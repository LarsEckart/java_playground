package lars.katas.dicegame;

import java.util.List;

public class Cup {

  private final List<Die> dice;

  public Cup(List<Die> dice) {
    this.dice = dice;
  }

  public void roll() {
    dice.forEach(Die::roll);
  }

  public int total() {
    return dice.stream().mapToInt(Die::faceValue).sum();
  }
}
