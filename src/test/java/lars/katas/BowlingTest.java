package lars.katas;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingTest {

  @Test
  void gutter_balls() {
    int[] rolls = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    Game game = new Game(rolls);

    assertThat(game.score()).isEqualTo(0);
  }

  @Test
  void open_frame() {
    int[] rolls = {0, 0, 5, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    Game game = new Game(rolls);

    assertThat(game.score()).isEqualTo(9);
  }

  @Test
  void another_open_frame() {
    int[] rolls = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1};

    Game game = new Game(rolls);

    assertThat(game.score()).isEqualTo(2);
  }

  @Test
  void spare() {
    int[] rolls = {0, 0, 5, 4, 6, 4, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    Game game = new Game(rolls);

    assertThat(game.score()).isEqualTo(24);
  }

  @Test
  void strike() {
    int[] rolls = {0, 0, 5, 4, 10, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    Game game = new Game(rolls);

    assertThat(game.score()).isEqualTo(27);
  }

  @Test
  void perfect_game() {
    int[] rolls = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};

    Game game = new Game(rolls);

    assertThat(game.score()).isEqualTo(300);
  }

  private static class Game {

    private final int[] rolls;
    private int frameStart;
    private int score;

    Game(int[] rolls) {
      this.rolls = rolls;
    }

    public int score() {
      for (int frame = 1; frame <= 10; frame++) {
        scoreIncrement();
        updateFrameStart();
      }
      return score;
    }

    private void scoreIncrement() {
      if (strike()) {
        score += 10 + rolls[frameStart + 1] + rolls[frameStart + 2];
      } else if (spare()) {
        score += 10 + rolls[frameStart + 2];
      } else {
        score += rolls[frameStart] + rolls[frameStart + 1];
      }
    }

    private void updateFrameStart() {
      if (strike()) {
        frameStart += 1;
      } else if (spare()) {
        frameStart += 2;
      } else {
        frameStart += 2;
      }
    }

    private boolean strike() {
      return rolls[frameStart] == 10;
    }

    private boolean spare() {
      return rolls[frameStart] + rolls[frameStart + 1] == 10;
    }
  }
}
