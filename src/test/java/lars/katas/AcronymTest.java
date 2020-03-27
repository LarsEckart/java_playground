package lars.katas;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AcronymTest {

  @Test
  void basic() {
    String phrase = "Portable Network Graphics";
    String expected = "PNG";
    assertThat(new Acronym(phrase).get()).isEqualTo(expected);
  }

  @Test
  void lowercaseWords() {
    String phrase = "Ruby on Rails";
    String expected = "ROR";
    assertThat(new Acronym(phrase).get()).isEqualTo(expected);
  }

  @Test
  void punctuation() {
    String phrase = "First In, First Out";
    String expected = "FIFO";
    assertThat(new Acronym(phrase).get()).isEqualTo(expected);
  }

  @Test
  void nonAcronymAllCapsWord() {
    String phrase = "GNU Image Manipulation Program";
    String expected = "GIMP";
    assertThat(new Acronym(phrase).get()).isEqualTo(expected);
  }

  @Test
  void punctuationWithoutWhitespace() {
    String phrase = "Complementary metal-oxide semiconductor";
    String expected = "CMOS";
    assertThat(new Acronym(phrase).get()).isEqualTo(expected);
  }
}
