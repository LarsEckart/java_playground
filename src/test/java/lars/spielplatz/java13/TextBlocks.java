package lars.spielplatz.java13;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TextBlocks {

  @Test
  void multi_line_strings() {
    String niceJson = """
    {
      "name": "Lars Eckart",
      "language": "Java"
    }"""; // same line to not append \n at the end
    assertThat(niceJson).isEqualTo("{\n"
        + "  \"name\": \"Lars Eckart\",\n"
        + "  \"language\": \"Java\"\n"
        + "}");
  }
}
