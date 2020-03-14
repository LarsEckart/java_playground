package lars.spielplatz.java12;

import java.time.DayOfWeek;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SwitchPreview {

  @Test
  void preview_feature() {
    assertThat(whatIsToday(DayOfWeek.SATURDAY)).isEqualTo("Weekend");
    assertThat(whatIsTodayAsExpression(DayOfWeek.MONDAY)).isEqualTo("work day");
  }

  String whatIsToday(DayOfWeek day) {
    var today = "";
    switch (day) {
      case SATURDAY:
      case SUNDAY:
        today = "Weekend";
        break;
      case MONDAY:
      case TUESDAY:
      case WEDNESDAY:
      case THURSDAY:
      case FRIDAY:
        today = "work day";
        break;
      default:
        today = "N/A";
    }
    return today;
  }

  String whatIsTodayAsExpression(DayOfWeek day) {
    return switch (day) {
      case SATURDAY, SUNDAY -> "Weekend";
      case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> "work day";
    };
  }

}
