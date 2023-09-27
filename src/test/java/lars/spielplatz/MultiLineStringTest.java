package lars.spielplatz;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
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
    String text4 = String.join(System.lineSeparator(), "a", "b", "c", "d");

    Assertions.assertAll(
        () -> assertThat(text).isEqualTo(text2),
        () -> assertThat(text2).isEqualTo(text3),
        () -> assertThat(text).isEqualTo(text3),
        () -> assertThat(text).isEqualTo(text4),
        () -> assertThat(text2).isEqualTo(text4),
        () -> assertThat(text3).isEqualTo(text4)

    );
  }

  @Test
  void line_endings_on_different_os2() {

      var text1 = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, "anySecretKey".getBytes(UTF_8))
          .hmacHex("%s%n%s%n%s%n%s".formatted("a", "b", "c", "d").getBytes(UTF_8))
          .toUpperCase();

      var text2 = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, "anySecretKey".getBytes(UTF_8))
          .hmacHex("%s\n%s\n%s\n%s".formatted("a", "b", "c", "d").getBytes(UTF_8))
          .toUpperCase();

      var text3 = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, "anySecretKey".getBytes(UTF_8))
          .hmacHex("""
              %s
              %s
              %s
              %s""".formatted("a", "b", "c", "d").getBytes(UTF_8))
          .toUpperCase();
    String text4 = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, "anySecretKey".getBytes(UTF_8))
        .hmacHex(String.join(System.lineSeparator(), "a", "b", "c", "d").getBytes(UTF_8))
        .toUpperCase();

    System.out.println("applesauce: " + text1);

    Assertions.assertAll(
        () -> assertThat(text1).isEqualTo(text2),
        () -> assertThat(text2).isEqualTo(text3),
        () -> assertThat(text1).isEqualTo(text3),
        () -> assertThat(text1).isEqualTo(text4),
        () -> assertThat(text2).isEqualTo(text4),
        () -> assertThat(text3).isEqualTo(text4)
    );
  }
}
