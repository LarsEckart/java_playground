package lars.katas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/*
Write the first failing test. Then write a factory that returns an object, or an aggregate of objects, that make the test pass.

The factory should be limited to creating objects and linking them together. No conditionals allowed.
 */
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

  @Test
  @Disabled
  void oneSpareAllGutter() {
    BowlingGame game = new BowlingGameFactory().create();
    game.roll(5);
    game.roll(5);
    game.roll(1);
    for (int i = 0; i < 17; i++) {
      game.roll(0);
    }
    assertEquals(12, game.score());
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
