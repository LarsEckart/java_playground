package lars.spielplatz;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;

class DateTimeExperiments {

  @Test
  void distance_between_dates_in_days() {
    LocalDateTime start = LocalDate.of(2020, 10, 13).atStartOfDay();
    LocalDateTime end = LocalDate.of(2020, 11, 15).atStartOfDay();

    assertThat(Duration.between(start, end).toDays()).isEqualTo(33L);
    assertThat(ChronoUnit.DAYS.between(start, end)).isEqualTo(33L);
  }
}
