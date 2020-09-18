package lars.katas.dicegame;

import java.util.List;

class DiceGame {

  private int balance;
  private final Cup dice;

  public DiceGame(Die d1, Die d2) {
    balance = 0;
    dice = new Cup(List.of(d1, d2));
  }

  public void play() {
    dice.roll();
    if (dice.total() > 7) {
      balance += 1;
    } else if (dice.total() < 7) {
      balance -= 1;
    }
  }

  public int balance() {
    return balance;
  }
}
