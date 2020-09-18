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
    if (sum > 7) {
      balance += 1;
    } else if (sum < 7) {
      balance -= 1;
    }
  }

  public int balance() {
    return balance;
  }
}
