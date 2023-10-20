package lars.katas.dicegame;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class Die_should {

  @Test
  void initially_be_between_1_and_6() {
    Die die = new Die();
    verifyDieValue(die);
  }

  @Test
  void distributed_well() {
    Die die = new Die();
    int[] outcome = new int[6];
    Arrays.fill(outcome, 0);
    for (int i = 0; i < 600000; i++) {
      die.roll();
      outcome[die.faceValue() - 1] = outcome[die.faceValue() - 1] + 1;
    }
    for (int j : outcome) {
      assertThat(j).isBetween(95000, 105000);
    }
  }

  @RepeatedTest(100)
  void roll_between_1_and_6() {
    Die die = new Die();
    die.roll();
    verifyDieValue(die);
  }

  private void verifyDieValue(Die die) {
    assertThat(die.faceValue()).isBetween(1, 6);
  }
}
