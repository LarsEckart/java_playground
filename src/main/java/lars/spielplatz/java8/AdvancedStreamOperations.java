package lars.spielplatz.java8;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class AdvancedStreamOperations {

  public static void main(String[] args) {

    List<Person> persons =
        List.of(
            new Person("Max", 18),
            new Person("Peter", 23),
            new Person("Pamela", 23),
            new Person("David", 12));

    List<Person> filtered =
        persons.stream().filter(p -> p.name.startsWith("P")).collect(Collectors.toList());

    Map<Integer, List<Person>> personsByAge =
        persons.stream().collect(Collectors.groupingBy(p -> p.age));

    personsByAge.forEach((age, p) -> System.out.format("age %s: %s\n", age, p));

    ForkJoinPool commonPool = ForkJoinPool.commonPool();
    System.out.println(commonPool.getParallelism());

    List.of("a1", "a2", "b1", "c2", "c1").parallelStream()
        .filter(
            s -> {
              System.out.format("filter: %s [%s]\n", s, Thread.currentThread().getName());
              return true;
            })
        .map(
            s -> {
              System.out.format("map: %s [%s]\n", s, Thread.currentThread().getName());
              return s.toUpperCase();
            })
        .forEach(s -> System.out.format("forEach: %s [%s]\n", s, Thread.currentThread().getName()));

    Map<Long, Person> personMap =
        Map.of(
            1L, new Person("Lars", 33),
            2L, new Person("Oskar", 2));

    personMap.entrySet().stream()
        .map(
            entry ->
                new Object() {
                  long id = entry.getKey();
                  String name = entry.getValue().name;
                })
        .forEach(tuple -> System.out.println(tuple.id + ": " + tuple.name));
  }

  static class Person {

    String name;
    int age;

    Person(String name, int age) {
      this.name = name;
      this.age = age;
    }

    @Override
    public String toString() {
      return name;
    }
  }
}
