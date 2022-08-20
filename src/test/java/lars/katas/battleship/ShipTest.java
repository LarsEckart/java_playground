package lars.katas.battleship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class ShipTest {

  @Test
  void isAlive() {
    Coordinate coordinate = new Coordinate(1);
    Ship ship = new Ship(List.of(coordinate));

    ship.shot(coordinate);

    assertThat(ship.isAlive()).isFalse();
  }
}
