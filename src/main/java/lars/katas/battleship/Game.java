package lars.katas.battleship;

import java.util.ArrayList;
import java.util.List;

public class Game {

  private List<Coordinate> coordinates = new ArrayList<>();
  private List<Coordinate> hitss = new ArrayList<>();

  public void addShipAt(Coordinate e, Coordinate e1) {
    addShipAt(e);
    addShipAt(e1);
  }

  public void addShipAt(Coordinate e, Coordinate e1, Coordinate e2) {
    addShipAt(e);
    addShipAt(e1);
    addShipAt(e2);
  }

  public void addShipAt(Coordinate c) {
    coordinates.add(c);
    hitss.add(c);
  }

  public String shoot(Coordinate p) {
    if (coordinates.contains(p)) {
      hitss.remove(p);
      if (hitss.isEmpty()) {
        return "sunk";
      }
      return "hit";
    }

    return "miss";
  }

  public boolean gameOver() {
    return hitss.isEmpty();
  }
}
