package lars.katas.battleship;

import java.util.ArrayList;
import java.util.List;

public class Game {

  private final List<Coordinate> coordinates = new ArrayList<>();
  private final List<Coordinate> hits = new ArrayList<>();

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
    hits.add(c);
  }

  public String shoot(Coordinate p) {
    if (coordinates.contains(p)) {
      hits.remove(p);
      if (hits.isEmpty()) {
        return "sunk";
      }
      return "hit";
    }

    return "miss";
  }

  public boolean gameOver() {
    return hits.isEmpty();
  }
}
