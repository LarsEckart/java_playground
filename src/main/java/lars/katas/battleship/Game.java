package lars.katas.battleship;

import java.util.ArrayList;
import java.util.List;

public class Game {

  private final List<Ship> ships = new ArrayList<>();

  public void addShipAt(Coordinate e, Coordinate e1) {
    ships.add(new Ship(List.of(e, e1)));
  }

  public void addShipAt(Coordinate e, Coordinate e1, Coordinate e2) {
    ships.add(new Ship(List.of(e, e1, e2)));
  }

  public String shoot(Coordinate p) {
    String result = "";
    for (Ship ship : ships) {
      result = ship.shot(p);
    }
    return result;
  }

  public boolean gameOver() {
    return this.ships.stream().noneMatch(Ship::isAlive);
  }

}
