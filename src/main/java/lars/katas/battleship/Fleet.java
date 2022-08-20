package lars.katas.battleship;

import java.util.ArrayList;
import java.util.List;

public class Fleet {

  private final List<Ship> ships;

  public Fleet() {
    ships = new ArrayList<>();
  }

  public void add(Ship ship) {
    ships.add(ship);
  }

  public String shoot(Coordinate p) {
    String result = "";
    for (Ship ship : ships) {
      result = ship.shot(p);
      if (result.equals("sunk")) {
        return "sunk";
      }
      if (result.equals("hit")) {
        return "hit";
      }
    }
    return result;
  }

  public boolean anyAlive() {
    return this.ships.stream().anyMatch(Ship::isAlive);
  }

}
