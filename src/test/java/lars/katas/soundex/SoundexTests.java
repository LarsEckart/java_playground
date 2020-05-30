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
    assertThat(soundex.encode("Ab")).isEqualTo("A100");
  }

  private static class Soundex {

    public String encode(String word) {
      var encoded = word.substring(0, 1);
      if (word.length() > 1) {
        encoded += "1";
      }
      return zeroPad(encoded);
    }

    private String zeroPad(String word) {
      var zeroesNeeded = 4 - word.length();
      return word + "0".repeat(zeroesNeeded);
    }
  }
}
