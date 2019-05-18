package lars.java11;

import com.google.common.flogger.FluentLogger;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

class AstroClientTest {

    private AstroClient client = new AstroClient();

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    @Test
    void getSync() {
        AstroResponse response = assertTimeoutPreemptively(
                Duration.ofSeconds(2),
                () -> client.getSync("http://api.open-notify.org/astros.json"));

        int num = response.getNumber();
        List<Assignment> assignments = response.getPeople();

        assertEquals("success", response.getMessage());
        assertEquals(num, assignments.size());
        assignments.forEach(assignment ->
                assertAll(() -> assertFalse(assignment.getName().isEmpty()),
                        () -> assertFalse(assignment.getCraft().isEmpty())));

        logResponse(num, assignments);
    }

    @Test
    void getAsync() {
        AstroResponse response = assertTimeoutPreemptively(
                Duration.ofSeconds(2),
                () -> client.getAsync("http://api.open-notify.org/astros.json").get());

        int num = response.getNumber();
        List assignments = response.getPeople();

        assertEquals("success", response.getMessage());
        assertEquals(num, assignments.size());

        logResponse(num, assignments);
    }

    private void logResponse(int num, List<Assignment> assignments) {
        logger.atInfo().log("There are %d people in space", num);
        assignments.forEach(person -> logger.atInfo().log("%s aboard %s",
                person.getName(),
                person.getCraft()));
    }
}
