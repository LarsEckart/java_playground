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
    game.addShipAt(1, 2, 3);

    String result = game.shoot(2);

    assertThat(result).isEqualTo("hit");
  }

  @Test
  void miss_when_shoot_where_ship_is() {
    Game game = new Game();
    game.addShipAt(1, 2, 3);

    String result = game.shoot(4);

    assertThat(result).isEqualTo("miss");
  }
}
