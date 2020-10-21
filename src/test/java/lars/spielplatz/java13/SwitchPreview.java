package lars.spielplatz.java13;

import java.time.DayOfWeek;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SwitchPreview {

  @Test
  void preview_feature() {
    assertThat(whatIsToday(DayOfWeek.SATURDAY)).isEqualTo("Weekend");
    assertThat(whatIsToday(DayOfWeek.MONDAY)).isEqualTo("work day");
  }

  private String whatIsToday(DayOfWeek s) {
    return switch (s) {
      case SATURDAY:
      case SUNDAY:
        yield "Weekend";
      case MONDAY:
      case TUESDAY:
      case WEDNESDAY:
      case THURSDAY:
      case FRIDAY:
        yield "work day";
    };
  }
}
