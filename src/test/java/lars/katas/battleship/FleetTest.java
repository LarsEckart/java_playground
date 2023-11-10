package lars.katas.battleship;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class FleetTest {

  @Test
  void none_alive_when_all_ship_shot() {
    Fleet fleet = new Fleet();
    fleet.add(new Ship(List.of(new Coordinate(1))));
    fleet.shoot(new Coordinate(1));

    assertThat(fleet.anyAlive()).isFalse();
  }

  @Test
  void alive_when_all_ship_shot() {
    Fleet fleet = new Fleet();
    fleet.add(new Ship(List.of(new Coordinate(1))));
    fleet.add(new Ship(List.of(new Coordinate(2))));
    fleet.shoot(new Coordinate(1));

    assertThat(fleet.anyAlive()).isTrue();
  }

  @Test
  void report_what_happened_when_shot_at() {
    Fleet fleet = new Fleet();
    fleet.add(new Ship(List.of(new Coordinate(1))));
    fleet.add(new Ship(List.of(new Coordinate(3))));

    String result = fleet.shoot(new Coordinate(1)).outcome();

    assertThat(result).isEqualTo("sunk");
  }
}
