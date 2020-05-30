package lars.katas.soundex;

import static org.assertj.core.api.Assertions.assertThat;

import org.jetbrains.annotations.NotNull;
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
      return zeroPad(head(word) + encodedDigits(word));
    }

    @NotNull
    private String head(String word) {
      return word.substring(0, 1);
    }

    private String encodedDigits(String word) {
      if (word.length() > 1) {
        return "1";
      }
      return "";
    }

    private String zeroPad(String word) {
      var zeroesNeeded = 4 - word.length();
      return word + "0".repeat(zeroesNeeded);
    }
  }
}
