package lars.katas.soundex;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SoundexTests {

  @Test
  void soundexEncodingRetainsSoleLetterOfOneLetterWord() {
    Soundex soundex = new Soundex();
    String encoded = soundex.encode("A");
    assertThat(encoded).isEqualTo("A000");
  }

  @Test
  void soundexEncodingPadsWithZerosToEnsureThreeDigits() {
    Soundex soundex = new Soundex();
    String encoded = soundex.encode("I");
    assertThat(encoded).isEqualTo("I000");
  }

  private static class Soundex {

    public String encode(String word) {
      return zeroPad(word);
    }

    private String zeroPad(String word) {
      return word + "000";
    }
  }
}
