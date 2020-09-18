package lars.katas.dicegame;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Loaded_Die_should {

  @Test
  void return_provided_value() throws Exception {
    Die die = new LoadedDie(5);
    assertThat(die.faceValue()).isEqualTo(5);
  }
}
