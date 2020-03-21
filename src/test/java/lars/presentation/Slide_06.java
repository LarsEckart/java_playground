package lars.presentation;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Slide_06 {

  @Test
  void optional() {
    Optional<String> middleName = findMiddleName();

    System.out.println("middle name: " + middleName.get());

    if (middleName.isPresent()) {
      System.out.println("middle name: " + middleName.get());
    }

    System.out.println("middle name: " + middleName.orElseThrow());

    middleName.ifPresent(m -> System.out.println("middle name: " + m));
  }

  private Optional<String> findMiddleName() {
    return Optional.of("any");
  }

  @Test
  void be_careful() {
    Optional<String> userFromDb = Optional.of("Lars");
    String result = userFromDb.map(this::runIfExists).orElse(runIfNotExists());

    assertThat(result).isEqualTo("Lars");

    // only meant to return default value, not to execute things!
    // use orElseGet in that case
  }

  private String runIfExists(String str) {
    System.out.println("run if exists");
    return str;
  }

  private String runIfNotExists() {
    System.out.println("insert into database");
    return "new user created";
  }

  interface Search {

    Optional<String> inMemory(long id);

    Optional<String> onDisk(long id);

    Optional<String> remotely(long id);

    default Optional<String> anywhere(long id) {
      return inMemory(id).or(() -> onDisk(id)).or(() -> remotely(id));
    }
  }

  @Test
  void if_present_or_else() {
    Optional<String> name = Optional.of("Lars");

    name.ifPresentOrElse(System.out::println, () -> System.out.println("No name"));
  }
}
