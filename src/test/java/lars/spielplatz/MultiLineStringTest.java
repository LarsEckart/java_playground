package lars.spielplatz;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

class MultiLineStringTest {

  @DisabledOnOs(OS.WINDOWS)
  @Test
  void line_endings_on_different_os() {
    String text = "%s%n%s%n%s%n%s".formatted("a", "b", "c", "d");
    String text2 = "%s\n%s\n%s\n%s".formatted("a", "b", "c", "d");
    String text3 =
        """
        %s
        %s
        %s
        %s"""
            .formatted("a", "b", "c", "d");
    String text4 = String.join(System.lineSeparator(), "a", "b", "c", "d");

    Assertions.assertAll(
        () -> assertThat(text).isEqualTo(text2),
        () -> assertThat(text2).isEqualTo(text3),
        () -> assertThat(text).isEqualTo(text3),
        () -> assertThat(text).isEqualTo(text4),
        () -> assertThat(text2).isEqualTo(text4),
        () -> assertThat(text3).isEqualTo(text4));
  }
}
