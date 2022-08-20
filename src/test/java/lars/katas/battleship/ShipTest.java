package lars.katas.battleship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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
    ship.shotLegacy(coordinate);

    assertThat(ship.isAlive()).isTrue();
  }

  @Test
  void ship_is_not_alive_when_fully_damaged() {
    Coordinate coordinate = new Coordinate(1);
    Ship ship = new Ship(List.of(coordinate));
    ship.shotLegacy(coordinate);

    assertThat(ship.isAlive()).isFalse();
  }

  @Test
  void hit_when_shoot_where_ship_is() {
    Ship ship = new Ship(List.of(new Coordinate(1), new Coordinate(2), new Coordinate(3)));

    String result = ship.shotLegacy(new Coordinate(2));

    assertThat(result).isEqualTo("hit");
  }

  @Test
  void miss_when_shoot_where_ship_not_is() {
    Ship ship = new Ship(List.of(new Coordinate(1), new Coordinate(2), new Coordinate(3)));

    String result = ship.shotLegacy(new Coordinate(4));

    assertThat(result).isEqualTo("miss");
  }

  @Test
  void sunk_when_shoot_where_ship_is_and_all_damaged() {
    Ship ship = new Ship(List.of(new Coordinate(1), new Coordinate(2)));

    ship.shotLegacy(new Coordinate(2));
    String result = ship.shotLegacy(new Coordinate(1));

    assertThat(result).isEqualTo("sunk");
  }

  @Test
  void do_we_support_2d_coordinates() {
    Ship ship = new Ship(List.of(new Coordinate("A1"), new Coordinate("A2")));

    ship.shotLegacy(new Coordinate("A2"));
    String result = ship.shotLegacy(new Coordinate("A1"));

    assertThat(result).isEqualTo("sunk");
  }

  @Test
  void coordinates_from_string_must_be_2_letters() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> new Coordinate("1"))
        .withMessage("not a valid coordinate, e.g. 'A1'");
  }
}
