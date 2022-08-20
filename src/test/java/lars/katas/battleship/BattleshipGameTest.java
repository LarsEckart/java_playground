package lars.katas.battleship;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.larseckart.tcr.CommitOnGreenExtension;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(CommitOnGreenExtension.class)
class BattleshipGameTest {


  @Test
  void not_game_over_when_ship_still_partly_alive() {
    Fleet game = new Fleet();
    game.add(new Ship(List.of(new Coordinate(1), new Coordinate(2))));
    game.shoot(new Coordinate(1));

    boolean result = game.gameOver();

    assertThat(result).isFalse();
  }

  @Test
  void game_over_when_ship_hit_everywhere() {
    Fleet game = new Fleet();
    game.add(new Ship(List.of(new Coordinate(1), new Coordinate(2))));
    game.shoot(new Coordinate(1));
    game.shoot(new Coordinate(2));

    boolean result = game.gameOver();

    assertThat(result).isTrue();
  }

  @Test
  void ship_sunk_when_last_position_hit() {
    Fleet game = new Fleet();
    game.add(new Ship(List.of(new Coordinate(1), new Coordinate(2))));
    game.shoot(new Coordinate(1));

    String result = game.shoot(new Coordinate(2));

    assertThat(result).isEqualTo("sunk");
  }
}
