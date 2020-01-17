package lars.katas;

import io.vavr.collection.Stream;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;

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
                .removeAll(unwantedWords::contains)
                .removeAll(w -> w.chars().allMatch(Character::isDigit))
                .map(String::toLowerCase)
                .map(w -> w.endsWith("s") ? w.substring(0, w.length()-1) : w)
                .collect(Collectors.toList());
    }

}
