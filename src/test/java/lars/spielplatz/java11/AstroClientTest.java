package lars.spielplatz.java11;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AstroClientTest {

  private AstroClient client = new AstroClient();

  private static final Logger logger = LoggerFactory.getLogger(AstroClientTest.class);

  @Disabled("tests started to timeout on when run by github action")
  @Test
  void getSync() {
    AstroResponse response =
        assertTimeoutPreemptively(
            Duration.ofSeconds(2), () -> client.getSync("http://api.open-notify.org/astros.json"));

    int num = response.number();
    List<Assignment> assignments = response.people();

    assertEquals("success", response.message());
    assertEquals(num, assignments.size());
    assignments.forEach(
        assignment ->
            assertAll(
                () -> assertFalse(assignment.name().isEmpty()),
                () -> assertFalse(assignment.craft().isEmpty())));

    logResponse(num, assignments);
  }

  @Disabled("tests started to timeout on when run by github action")
  @Test
  void getAsync() {
    AstroResponse response =
        assertTimeoutPreemptively(
            Duration.ofSeconds(2),
            () -> client.getAsync("http://api.open-notify.org/astros.json").get());

    int num = response.number();
    List<Assignment> assignments = response.people();

    assertEquals("success", response.message());
    assertEquals(num, assignments.size());

    logResponse(num, assignments);
  }

  private void logResponse(int num, List<Assignment> assignments) {
    logger.info("There are {} people in space", num);
    assignments.forEach(person -> logger.info("{} aboard {}", person.name(), person.craft()));
  }
}
