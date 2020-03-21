package lars.presentation;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import static java.util.function.Predicate.not;

public class Slide_05 {

  @Test
  void streams_iterate() {
    for (int i = 0; i < 10; i++) {
      System.out.println(i);
    }

    Stream.iterate(0, i -> i < 10, i -> i + 1).forEach(System.out::println);
  }

  @Test
  void stream_of_nullable() {
    Stream.ofNullable("Lars").filter(s -> s.startsWith("L")).forEach(System.out::println);
  }

  @Test
  void drop_while() {
    Stream.iterate(0, i -> i < 10, i -> i + 1).dropWhile(i -> i < 5).forEach(System.out::println);
  }

  @Test
  void take_while() {
    Stream.iterate(0, i -> i < 10, i -> i + 1).takeWhile(i -> i < 5).forEach(System.out::println);
  }

  @Test
  void new_collectors() {
    Stream.iterate(0, i -> i < 10, i -> i + 1).collect(Collectors.toUnmodifiableList());
  }

  @Test
  void new_predicate() {
    String names = "hello\nLars\nOskar";
    names.lines().filter(s -> !s.isBlank()).forEach(System.out::println);

    names.lines().filter(Predicate.not(String::isBlank)).forEach(System.out::println);

    names.lines().filter(not(String::isBlank)).forEach(System.out::println);
  }
}
