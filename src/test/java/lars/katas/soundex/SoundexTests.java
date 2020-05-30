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

  @Test
  void soundexEncodingIgnoresNonAlphabetics() {
    assertThat(soundex.encode("A#")).isEqualTo("A000");
  }

  @Test
  void soundexEncodingReplacesMultipleConsonantsWithDigits() {
    assertThat(soundex.encode("Acdl")).isEqualTo("A234");
  }

  @Test
  void soundexEncodingLimitsLengthToFourCharacters() {
    assertThat(soundex.encode("Dcdlb")).hasSizeLessThan(5);
  }

  @Test
  void soundexEncodingIgnoresVowelLikeLetters() {
    assertThat(soundex.encode("Baeiouhycdl")).isEqualTo("B234");
  }

  @Test
  void soundexEncodingCombinesDuplicateEncodings() {
    assertThat(soundex.encode("Abfcgdt")).isEqualTo("A123");
  }

}
