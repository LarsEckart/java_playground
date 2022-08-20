package lars.katas.battleship;

import java.util.ArrayList;
import java.util.List;

public class Game {

  private List<Integer> list = new ArrayList<>();
  private List<Integer> hits = new ArrayList<>();

  public void addShipAt(int... i) {
    for (int i1 : i) {
      list.add(i1);
      hits.add(i1);
    }
  }

  public String shoot(int p) {
    if (list.contains(p)) {
      hits.remove((Integer) p);
      return "hit";
    }

    return "miss";
  }

  public boolean gameOver() {
    return hits.isEmpty();
  }
}
