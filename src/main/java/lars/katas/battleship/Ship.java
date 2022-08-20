package lars.katas.battleship;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Ship {

  private final String name;
  private final Set<Coordinate> coordinates;
  private final Set<Coordinate> undamaged;

  public Ship(List<Coordinate> coordinates) {
    this("any", coordinates);
  }

  public Ship(String name, List<Coordinate> coordinates) {
    this.name = name;
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
        return new Result(name, "sunk");
      }
      return new Result(name, "hit");
    }

    return new Result(name, "miss");
  }
}
