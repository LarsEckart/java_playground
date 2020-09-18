package lars.katas.dicegame;

import java.util.List;

class DiceGame {

  private final List<Die> dice;
  private int balance;
  private int sum;

  public DiceGame(Die d1, Die d2) {
    this.balance = 0;
    this.dice = List.of(d1, d2);
  }

  public void play() {
    for (Die die : dice) {
      die.roll();
      sum += die.faceValue();
    }
  }

  public int balance() {
    if (sum > 7) {
      return 1;
    }
    return -1;
  }
}
