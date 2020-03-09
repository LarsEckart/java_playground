package lars.katas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BowlingOcp {

  @Test
  void gutterGame() {
    BowlingGame game = new BowlingGameFactory().create();
    for (int i = 0; i < 20; i++) {
      game.roll(0);
    }
    assertEquals(0, game.score());
  }

  @Test
  void allOnesGame() {
    BowlingGame game = new BowlingGameFactory().create();
    for (int i = 0; i < 20; i++) {
      game.roll(1);
    }
    assertEquals(20, game.score());
  }

  interface Rolls {
    void add(int n);

    int sum();
  }

  static class BowlingGame {
    private final Rolls rolls;

    public BowlingGame(Rolls rolls) {
      this.rolls = rolls;
    }

    public void roll(int n) {
      rolls.add(n);
    }

    public int score() {
      return rolls.sum();
    }
  }

  static class BowlingGameFactory {
    public BowlingGame create() {
      return new BowlingGame(new Accumulator());
    }
  }

  static class Accumulator implements Rolls {
    private int result;

    @Override
    public void add(int n) {
      result += n;
    }

    @Override
    public int sum() {
      return result;
    }
  }
}
