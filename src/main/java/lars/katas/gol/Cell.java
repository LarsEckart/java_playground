package lars.katas.gol;

import java.util.List;

public class Cell {
  static final int ALIVE = 1;
  static final int DEAD = 0;

  int state;

  public Cell() {
    state = ALIVE;
  }

  public Cell(int state) {
    this.state = state;
  }

  public void next(List neighbors) {
    if (neighbors.size() < 2 || neighbors.size() > 3) {
      state = DEAD;
    }
    if (neighbors.size() == 3) {
      state = ALIVE;
    }
  }

  public int state() {
    return state;
  }

  public static Cell dead() {
    return new Cell(DEAD);
  }

  public String toString() {
    return state == ALIVE ? "*" : ".";
  }
}
