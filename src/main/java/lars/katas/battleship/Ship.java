package lars.katas.battleship;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Ship {

  private final Set<Coordinate> coordinates;
  private final Set<Coordinate> undamaged;

  public Ship(List<Coordinate> coordinates) {
    this.coordinates = new HashSet<>(coordinates);
    this.undamaged = new HashSet<>(coordinates);
  }

  public boolean isAlive() {
    return !undamaged.isEmpty();
  }

  public Result shot(Coordinate p) {
    if (coordinates.contains(p)) {
      undamaged.remove(p);
      if (undamaged.isEmpty()) {
        return new Result("sunk");
      }
      return new Result("hit");
    }

    return new Result("miss");
  }
}
