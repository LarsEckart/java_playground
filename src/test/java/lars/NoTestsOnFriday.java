package lars;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import org.junit.jupiter.api.Test;

class NoTestsOnFriday {

  @DisabledOnWeekdays(DayOfWeek.FRIDAY)
  @Test
  void applesauce() {
    assertEquals(4, 2 + 2);
  }
}
