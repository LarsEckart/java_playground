package lars.katas.dicegame;

import java.util.List;

class DiceGame {

  private final List<Die> dice;
  private int balance;

  public DiceGame(Die d1, Die d2) {
    this.balance = 0;
    this.dice = List.of(d1, d2);
  }

  public void play() {

  }

  public int balance() {
    return -1;
  }
}
