package lars.presentation;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Slide_04 {

    @Test
    void new_collection_factory_methods() {
        List<String> list = List.of("Lars", "Oskar");
        Set<String> set = Set.of("Lars", "Oskar");
        Map<Long, String> map = Map.of(1L, "Lars", 2L, "Oskar");
        Map<Long, String> mapEntries = Map.ofEntries(
                Map.entry(1L, "Lars"),
                Map.entry(2L, "Oskar")
        );
    }

    @Test
    void unmodifiable() {
        var stringList = List.of("Lars", "Oskar");
        assertThrows(UnsupportedOperationException.class, () -> stringList.add("Peppa"));
        assertThrows(UnsupportedOperationException.class, () -> stringList.remove("Lars"));
        assertThrows(UnsupportedOperationException.class, stringList::clear);
    }

    @Test
    void but_not_immutable() {
        var lars = new Person("Lars");
        var persons = List.of(lars, new Person("Oskar"));
        assertThat(persons).containsExactly(new Person("Lars"), new Person("Oskar"));
        lars.name = "Larson";
        assertThat(persons).containsExactly(new Person("Larson"), new Person("Oskar"));
    }

    @Test
    void not_same_as_collections_unmodifiable_list() {
        Person[] peoples = {new Person("Lars"), new Person("Oskar")};
        List<Person> inner = Arrays.asList(peoples);
        List<Person> unmodifiableList = Collections.unmodifiableList(inner);

        List<Person> factoryList = List.of(peoples);

        assertThat(unmodifiableList).containsExactly(new Person("Lars"), new Person("Oskar"));
        assertThat(factoryList).containsExactly(new Person("Lars"), new Person("Oskar"));

        //inner.set(0, new Person("Larson"));
        peoples[0] = new Person("Larson");
        assertThat(unmodifiableList).containsExactly(new Person("Larson"), new Person("Oskar"));
        assertThat(factoryList).containsExactly(new Person("Lars"), new Person("Oskar"));
    }

    @Test
    void no_nulls() {
        assertThrows(NullPointerException.class, () -> List.of("Hello", null, "Lars"));
    }

    @Test
    void no_duplicates() {
        assertThrows(IllegalArgumentException.class, () -> Set.of("Lars", "Oskar", "Lars"));
    }

    @Test
    void copy_of_allows_duplicates() {
        List<String> names = Arrays.asList("Lars", "Oskar", "Lars");

        var namesList = List.copyOf(names);
        assertThat(namesList).containsExactly("Lars", "Oskar", "Lars");

        var namesSet = Set.copyOf(names);
        assertThat(namesSet).containsExactlyInAnyOrder("Lars", "Oskar");
    }

    class Person {

        public String name;

        public Person(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Person person = (Person) o;
            return Objects.equals(name, person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
