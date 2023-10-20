package lars.spielplatz.java13;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/** https://cr.openjdk.java.net/~jlaskey/Strings/TextBlocksGuide_v11.html */
class TextBlocks {

  @Test
  void multi_line_strings() {
    String niceJson =
        """
        {
          "name": "Lars Eckart",
          "language": "Java"
        }"""; // same line to not append \n at the end
    assertThat(niceJson)
        .isEqualTo("{\n" + "  \"name\": \"Lars Eckart\",\n" + "  \"language\": \"Java\"\n" + "}");
  }

  @Test
  void improves_the_clarity_of_the_code_particularly_with_multi_line_strings() throws Exception {
    // ORIGINAL
    String message =
        "'The time has come,' the Walrus said,\n"
            + "'To talk of many things:\n"
            + "Of shoes -- and ships -- and sealing-wax --\n"
            + "Of cabbages -- and kings --\n"
            + "And why the sea is boiling hot --\n"
            + "And whether pigs have wings.'\n";

    // BETTER
    String betterMessage =
        """
        'The time has come,' the Walrus said,
        'To talk of many things:
        Of shoes -- and ships -- and sealing-wax --
        Of cabbages -- and kings --
        And why the sea is boiling hot --
        And whether pigs have wings.'
        """;
  }

  @Test
  void Use_embedded_escape_sequences_when_they_maintain_readability() throws Exception {
    var data =
        """
        Name | Address | City
        Bob Smith | 123 Anytown St\nApt 100 | Vancouver
        Jon Brown | 1000 Golden Place\nSuite 5 | Santa Ana
        """;
  }

  @Test
  void alignment() throws Exception {
    String string = """
        red
        green
        blue
        """;
  }

  @Test
  void
      recommended_to_fully_left_justify_a_wide_string_in_order_to_avoid_horizontal_scrolling_or_line_wrapping()
          throws Exception {
    String lilacs =
        """
Over the breast of the spring, the land, amid cities,
Amid lanes and through old woods, where lately the violets peep’d from the ground, spotting the gray debris,
Amid the grass in the fields each side of the lanes, passing the endless grass,
Passing the yellow-spear’d wheat, every grain from its shroud in the dark-brown fields uprisen,
Passing the apple-tree blows of white and pink in the orchards,
Carrying a corpse to where it shall rest in the grave,
Night and day journeys a coffin.
""";
    System.out.println(lilacs);
  }
}
