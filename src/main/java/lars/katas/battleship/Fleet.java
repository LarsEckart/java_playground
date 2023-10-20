package lars.katas.battleship;

import java.util.ArrayList;
import java.util.List;

class Fleet {

  private final List<Ship> ships;

  public Fleet() {
    ships = new ArrayList<>();
  }

  public void add(Ship ship) {
    ships.add(ship);
  }

  public Result shoot(Coordinate p) {
    Result result = new Result("miss");
    for (Ship ship : ships) {
      result = ship.shot(p);
      if (result.outcome().equals("sunk")) {
        return result;
      }
      if (result.outcome().equals("hit")) {
        return result;
      }
    }
    return result;
  }

  public boolean anyAlive() {
    return this.ships.stream().anyMatch(Ship::isAlive);
  }
}
