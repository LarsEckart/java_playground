package lars.katas;

import static java.util.function.Predicate.not;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class QueryParserTest {

  @Test
  void name() {
    assertThat(parse("guitar")).isEqualTo(List.of("guitar"));
    assertThat(parse("red  electric guitar")).isEqualTo(List.of("red", "electric", "guitar"));
    assertThat(parse("red guitar with gigbag")).isEqualTo(List.of("red", "guitar", "gigbag"));
    assertThat(parse("red guitar and gigbag")).isEqualTo(List.of("red", "guitar", "gigbag"));
    assertThat(parse("red guitar with 7 gigbag")).isEqualTo(List.of("red", "guitar", "gigbag"));
    assertThat(parse("Guitar")).isEqualTo(List.of("guitar"));
    assertThat(parse("guitars")).isEqualTo(List.of("guitar"));
  }

  List<String> parse(String input) {
    Set<String> unwantedWords = Set.of("and", "with");

    return Stream.of(input.split(" "))
        .filter(not(unwantedWords::contains))
        .filter(w -> !w.chars().allMatch(Character::isDigit))
        .map(String::toLowerCase)
        .map(w -> w.endsWith("s") ? w.substring(0, w.length() - 1) : w)
        .collect(Collectors.toList());
  }
}
