package lars.katas.battleship;

import java.util.ArrayList;
import java.util.List;

class Ship {

  private final List<Coordinate> coordinates;
  private final List<Coordinate> undamaged;

  public Ship(List<Coordinate> coordinates) {
    this.coordinates = coordinates;
    this.undamaged = new ArrayList<>(coordinates);
  }

  public boolean isAlive() {
    return !undamaged.isEmpty();
  }

  public String shot(Coordinate p) {
    if (coordinates.contains(p)) {
      undamaged.remove(p);
      if (undamaged.isEmpty()) {
        return "sunk";
      }
      return "hit";
    }

    return "miss";
  }
}
