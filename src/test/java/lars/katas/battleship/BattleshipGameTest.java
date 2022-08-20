package lars.katas.battleship;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.larseckart.tcr.CommitOnGreenExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(CommitOnGreenExtension.class)
class BattleshipGameTest {

  @Test
  void hit_when_shoot_where_ship_is() {
    Game game = new Game();
    game.addShipAt(new Coordinate(1), new Coordinate(2), new Coordinate(3));

    String result = game.shoot(new Coordinate(2));

    assertThat(result).isEqualTo("hit");
  }

  @Test
  void miss_when_shoot_where_ship_is() {
    Game game = new Game();
    game.addShipAt(new Coordinate(1), new Coordinate(2), new Coordinate(3));

    String result = game.shoot(new Coordinate(4));

    assertThat(result).isEqualTo("miss");
  }

  @Test
  void not_game_over_when_ship_still_fully_alive() {
    Game game = new Game();
    game.addShipAt(new Coordinate(1), new Coordinate(2));

    boolean result = game.gameOver();

    assertThat(result).isFalse();
  }

  @Test
  void not_game_over_when_ship_still_partly_alive() {
    Game game = new Game();
    game.addShipAt(new Coordinate(1), new Coordinate(2));
    game.shoot(new Coordinate(1));

    boolean result = game.gameOver();

    assertThat(result).isFalse();
  }

  @Test
  void game_over_when_ship_hit_everywhere() {
    Game game = new Game();
    game.addShipAt(new Coordinate(1), new Coordinate(2));
    game.shoot(new Coordinate(1));
    game.shoot(new Coordinate(2));

    boolean result = game.gameOver();

    assertThat(result).isTrue();
  }

  @Test
  void ship_sunk_when_last_position_hit() {
    Game game = new Game();
    game.addShipAt(new Coordinate(1), new Coordinate(2));
    game.shoot(new Coordinate(1));

    String result = game.shoot(new Coordinate(2));

    assertThat(result).isEqualTo("sunk");
  }
}
