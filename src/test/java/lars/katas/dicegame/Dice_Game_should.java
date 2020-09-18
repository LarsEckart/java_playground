package lars.katas.dicegame;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Dice_Game_should {

  @Test
  void decrease_balance_for_loss() throws Exception {
    Die d1 = new LoadedDie(3);
    Die d2 = new LoadedDie(3);
    DiceGame game = new DiceGame(d1, d2);
    game.play();
    assertThat(game.balance()).isEqualTo(-1);
  }
}
