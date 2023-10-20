package lars.spielplatz.java10;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class CollectionsTest {

  @Test
  void name() {
    List<String> list = List.of("Testing", "Immutable", "List", "From", "Stream");

    List<String> collectedList = list.stream().collect(Collectors.toList());
    collectedList.set(0, "hello");
    List<String> immutableList = list.stream().collect(Collectors.toUnmodifiableList());
    immutableList.forEach(System.out::println);

    assertThrows(UnsupportedOperationException.class, () -> immutableList.set(0, "hello"));

    Map<String, Integer> wordsLengths =
        list.stream()
            .collect(Collectors.toUnmodifiableMap(Function.identity(), word -> word.length()));
    wordsLengths.forEach((word, length) -> System.out.println(word + " -> " + length + " letters"));
  }
}
