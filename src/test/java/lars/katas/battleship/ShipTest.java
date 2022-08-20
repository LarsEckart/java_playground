package lars.katas.battleship;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class ShipTest {

  @Test
  void ship_is_alive_when_not_damaged_at_all() {
    Coordinate coordinate = new Coordinate(1);
    Ship ship = new Ship(List.of(coordinate));

    assertThat(ship.isAlive()).isTrue();
  }

  @Test
  void ship_is_alive_when_only_partly_damaged() {
    Coordinate coordinate = new Coordinate(1);
    Ship ship = new Ship(List.of(coordinate, new Coordinate(2)));
    ship.shot(coordinate);

    assertThat(ship.isAlive()).isTrue();
  }

  @Test
  void ship_is_not_alive_when_fully_damaged() {
    Coordinate coordinate = new Coordinate(1);
    Ship ship = new Ship(List.of(coordinate));
    ship.shot(coordinate);

    assertThat(ship.isAlive()).isFalse();
  }

  @Test
  void hit_when_shoot_where_ship_is() {
    Ship ship = new Ship(List.of(new Coordinate(1), new Coordinate(2), new Coordinate(3)));

    String result = ship.shot(new Coordinate(2));

    assertThat(result).isEqualTo("hit");
  }

  @Test
  void miss_when_shoot_where_ship_is() {
    Ship ship = new Ship(List.of(new Coordinate(1), new Coordinate(2), new Coordinate(3)));

    String result = ship.shot(new Coordinate(4));

    assertThat(result).isEqualTo("miss");
  }
}
