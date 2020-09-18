package lars.katas.dicegame;

import java.util.List;

public class Cup {

  private final List<Die> dice;

  public Cup(List<Die> dice) {
    this.dice = dice;
  }

  int total() {
    int total = 0;
    for (Die die : dice) {
      die.roll();
      total += die.faceValue();
    }
    return total;
  }
}
