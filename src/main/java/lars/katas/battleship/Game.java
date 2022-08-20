package lars.katas.battleship;

import java.util.ArrayList;
import java.util.List;

public class Game {

  private List<Integer> list = new ArrayList<>();

  public void addShipAt(int p1, int p2, int p3) {
    list.add(p1);
    list.add(p2);
    list.add(p3);
  }

  public String shoot(int p) {
    if (list.contains(p)) {
      return "hit";
    }

    return "miss";
  }
}
