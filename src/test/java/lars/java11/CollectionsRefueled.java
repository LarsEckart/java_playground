package lars.java11;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CollectionsRefueled {

    @Test
    void be_unmodifiable() {
        var stringList = List.of("abc", "def");
        assertThrows(UnsupportedOperationException.class, () -> stringList.add("lars"));
        assertThrows(UnsupportedOperationException.class, () -> stringList.remove("abc"));
        assertThrows(UnsupportedOperationException.class, stringList::clear);
    }

    @Test
    void but_not_immutable() {
        Person lars = new Person("Lars");
        var persons = List.of(lars, new Person("Oskar"));
        assertThat(persons).containsExactly(new Person("Lars"), new Person("Oskar"));
        lars.name = "Larson";
        assertThat(persons).containsExactly(new Person("Larson"), new Person("Oskar"));
    }

    @Test
    void not_same_as_collections_unmodifiable_list() {
        List<Person> inner = Arrays.asList(new Person("Lars"), new Person("Oskar"));
        List<Person> list1 = Collections.unmodifiableList(inner);
        List<Person> list2 = List.of(new Person("Lars"), new Person("Oskar"));
        assertThat(list1).containsExactly(new Person("Lars"), new Person("Oskar"));
        assertThat(list2).containsExactly(new Person("Lars"), new Person("Oskar"));
        inner.set(0, new Person("Larson"));
        assertThat(list1).containsExactly(new Person("Larson"), new Person("Oskar"));
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
        List<String> namesList = List.copyOf(names);
        assertThat(namesList).containsExactly("Lars", "Oskar", "Lars");

        Set<String> namesSet = Set.copyOf(names);
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
