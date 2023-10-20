package lars.junit5;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class AssertJExamples {

  @Disabled("demo for failure message")
  @Test
  void express_intent_date() {
    Date birthday = Date.from(Instant.parse("2020-12-03T10:15:30.00Z"));

    Date today = new Date();

    assertThat(birthday).isBefore(today);
    assertTrue(birthday.getTime() < today.getTime());
  }

  @Disabled("demo for failure message")
  @Test
  void express_intent_local_date() {
    LocalDate birthday = LocalDate.of(2020, 8, 6);

    LocalDate today = LocalDate.now();

    assertThat(birthday).isBefore(today);
    assertTrue(birthday.isBefore(today));
  }
}
