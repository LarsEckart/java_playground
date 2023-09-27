package lars.spielplatz;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MultiLineStringTest {

  @Test
  void line_endings_on_different_os() {
    String text = "%s%n%s%n%s%n%s".formatted("a", "b", "c", "d");
    String text2 = "%s\n%s\n%s\n%s".formatted("a", "b", "c", "d");
    String text3 = """
        %s
        %s
        %s
        %s""".formatted("a", "b", "c", "d");

    Assertions.assertAll(
        () -> assertThat(text).isEqualTo(text2),
        () -> assertThat(text2).isEqualTo(text3),
        () -> assertThat(text).isEqualTo(text3)
    );
  }
}
