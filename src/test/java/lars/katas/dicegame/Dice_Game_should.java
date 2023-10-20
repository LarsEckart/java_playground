package lars.katas.dicegame;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Dice_Game_should {

  @Test
  void decrease_balance_for_loss() {
    Die d1 = new LoadedDie(3);
    Die d2 = new LoadedDie(3);
    DiceGame game = new DiceGame(d1, d2);
    game.play();
    assertThat(game.balance()).isEqualTo(-1);
  }

  @Test
  void increase_balance_for_win() {
    Die d1 = new LoadedDie(4);
    Die d2 = new LoadedDie(4);
    DiceGame game = new DiceGame(d1, d2);
    game.play();
    assertThat(game.balance()).isEqualTo(1);
  }

  @Test
  void leave_balance_alone_for_push() {
    Die d1 = new LoadedDie(4);
    Die d2 = new LoadedDie(3);
    DiceGame game = new DiceGame(d1, d2);
    game.play();
    assertThat(game.balance()).isEqualTo(0);
  }
}
