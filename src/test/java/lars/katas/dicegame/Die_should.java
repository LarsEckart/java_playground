package lars.katas.dicegame;

import com.github.larseckart.tcr.TestCommitRevertMainExtension;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(TestCommitRevertMainExtension.class)
class Die_should {

  @Test
  void initially_be_between_1_and_6() throws Exception {
    Die die = new Die();
    verifyDieValue(die);
  }

  @RepeatedTest(1000)
  void roll_between_1_and_6() throws Exception {
    Die die = new Die();
    die.roll();
    verifyDieValue(die);
  }

  private void verifyDieValue(Die die) {
    assertThat(die.faceValue()).isGreaterThanOrEqualTo(1);
    assertThat(die.faceValue()).isLessThanOrEqualTo(6);
  }
}
