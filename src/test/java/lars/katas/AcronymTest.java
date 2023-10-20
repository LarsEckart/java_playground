package lars.katas;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

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

  @Test
  void veryLongAbbreviation() {
    String phrase = "Rolling On The Floor Laughing So Hard That My Dogs Came Over And Licked Me";
    String expected = "ROTFLSHTMDCOALM";
    assertThat(new Acronym(phrase).get()).isEqualTo(expected);
  }

  @Test
  void consecutiveDelimiters() {
    String phrase = "Something - I made up from thin air";
    String expected = "SIMUFTA";
    assertThat(new Acronym(phrase).get()).isEqualTo(expected);
  }

  @Test
  void apostrophes() {
    String phrase = "Halley's Comet";
    String expected = "HC";
    assertThat(new Acronym(phrase).get()).isEqualTo(expected);
  }

  @Test
  void underscoreEmphasis() {
    String phrase = "The Road _Not_ Taken";
    String expected = "TRNT";
    assertThat(new Acronym(phrase).get()).isEqualTo(expected);
  }
}
