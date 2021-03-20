package lars.spielplatz.java16;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class RecordsTest {

  @Test
  void grants_access_to_fields() {
    Person person = new Person("Oskar", 4);
    assertThat(person.name()).isEqualTo("Oskar");
    assertThat(person.age()).isEqualTo(4);
  }

  @Test
  void has_generated_toString() {
    Person person = new Person("Oskar", 4);
    assertThat(person.toString()).isEqualTo("Person[name=Oskar, age=4]");
  }

  @Test
  void can_have_static_fields_and_methods() {
    Person person = new Person("Oskar", 4);
    assertThat(Person.getInstanceCounter()).isGreaterThan(0);
  }

  @Test
  void can_change_constructor_behaviour_to_throw_exception() {
    assertThrows(IllegalArgumentException.class, () -> new Person("Oskar", -1));
  }
}
