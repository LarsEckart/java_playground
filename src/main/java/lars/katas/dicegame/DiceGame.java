package lars.katas.dicegame;

import java.util.List;

class DiceGame {

  private int balance;
  private final Cup cup;

  public DiceGame(Die d1, Die d2) {
    balance = 0;
    cup = new Cup(List.of(d1, d2));
  }

  public void play() {
    cup.roll();
    if (cup.total() > 7) {
      balance += 1;
    } else if (cup.total() < 7) {
      balance -= 1;
    }
  }

  public int balance() {
    return balance;
  }
}
