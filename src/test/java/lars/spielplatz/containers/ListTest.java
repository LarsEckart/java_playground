package lars.spielplatz.containers;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

public class ListTest {

  @Test
  void ArrayList_is_a_modifiable_list() {
    List<Integer> list = new ArrayList<>();

    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);

    assertThat(list).isInstanceOf(ArrayList.class);
    assertThat(list).contains(1, 2, 3, 4);
  }

  @Test
  void List_of_returns_unmodifiable_list() {
    List<Integer> list = List.of(1, 2, 3);

    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(() -> list.add(4));
  }

  @Test
  void collectors_toList_returns_modifiable_ArrayList() {
    // There are no guarantees on the type, mutability, serializability, or thread-safety of the
    // List returned; if more control over the returned List is required, use toCollection(Supplier).
    // currently on java 17 we get back an ArrayList
    List<Integer> list = Stream.of(1, 2, 3).collect(Collectors.toList());

    list.add(4);

    assertThat(list).contains(1, 2, 3, 4);
    assertThat(list).isInstanceOf(ArrayList.class);
  }

  @Test
  void toList_returns_unmodifiable_list() {
    // (unmodifiable != immutable)
    // Collections that do not support modification operations (such as add , remove and clear ) are
    // referred to as unmodifiable. Collections that are not unmodifiable are modifiable.
    // Collections that additionally guarantee that no change in the Collection object will be
    // visible are referred to as immutable.
    List<Integer> list = Stream.of(1, 2, 3).toList();

    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(() -> list.add(4));
  }

  @Test
  void example_of_treating_a_returned_list_as_unmodifiable_even_when_it_is_not() {
    List<Integer> list = listWithOneTwoThree();

    List<Integer> myNumbers = new ArrayList<>(list);
    myNumbers.add(4);

    assertThat(myNumbers).containsExactly(1, 2, 3, 4);
    assertThat(list).containsExactly(1, 2, 3);
  }

  private static List<Integer> listWithOneTwoThree() {
    return Stream.of(1, 2, 3).collect(Collectors.toList());
  }
}
