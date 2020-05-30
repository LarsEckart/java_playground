package lars.katas.soundex;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SoundexTests {

  private Soundex soundex;

  @BeforeEach
  void setUp() {
    soundex = new Soundex();
  }

  @Test
  void soundexEncodingRetainsSoleLetterOfOneLetterWord() {
    assertThat(soundex.encode("A")).isEqualTo("A000");
  }

  @Test
  void soundexEncodingPadsWithZerosToEnsureThreeDigits() {
    assertThat(soundex.encode("I")).isEqualTo("I000");
  }

  @Test
  void soundexEncodingReplacesConsonantsWithAppropriateDigits() {
    assertThat(soundex.encode("Ax")).isEqualTo("A200");
  }

}
