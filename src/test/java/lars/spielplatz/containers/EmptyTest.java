package lars.spielplatz.containers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class EmptyTest {

  @Test
  void will_throw_when_checking_contains_null() {
    assertThrows(NullPointerException.class, () -> List.<String>of().contains(null));
  }

  @Test
  void will_NOT_throw_when_checking_contains_null() {
    assertThat(Collections.<String>emptyList().contains(null)).isFalse();
  }

  @Test
  void arrays_as_list_is_modifyable() {
    List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6);
    integers.set(0, 0);
    assertThat(integers.get(0)).isEqualTo(0);
  }

  @Test
  void list_of_is_not_modifyable() {
    List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);
    assertThrows(UnsupportedOperationException.class, () -> integers.set(0, 0));
  }
}
