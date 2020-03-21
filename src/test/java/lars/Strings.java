package lars;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Strings {

  @Test
  void codePoints_to_string_list_with_Character_toString() throws Exception {
    String name = "Lars";

    List<String> collect =
        name.codePoints()
            .mapToObj(cp -> Character.toString((char) cp))
            .collect(Collectors.toList());

    assertThat(collect).isEqualTo(List.of("L", "a", "r", "s"));
  }

  @Test
  void codePoints_to_string_list_with_special_trick() throws Exception {
    String name = "Lars";

    List<String> collect =
        name.codePoints().mapToObj(cp -> "" + (char) cp).collect(Collectors.toList());

    assertThat(collect).isEqualTo(List.of("L", "a", "r", "s"));
  }

  @Test
  void codePoints_with_numbers() throws Exception {
    String name = "1337";

    List<String> collect =
        name.codePoints()
            .mapToObj(cp -> Character.toString((char) cp))
            .collect(Collectors.toList());

    assertThat(collect).isEqualTo(List.of("1", "3", "3", "7"));
  }

  @Test
  void chars_to_string_list_with_Character_toString() throws Exception {
    String name = "Lars";

    List<String> collect =
        name.chars().mapToObj(c -> Character.toString((char) c)).collect(Collectors.toList());

    assertThat(collect).isEqualTo(List.of("L", "a", "r", "s"));
  }
}
