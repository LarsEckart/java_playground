package lars.katas.dicegame;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Loaded_Die_should {

  @Test
  void return_provided_value() throws Exception {
    Die die = new LoadedDie(5);
    assertThat(die.faceValue()).isEqualTo(5);
  }
}
