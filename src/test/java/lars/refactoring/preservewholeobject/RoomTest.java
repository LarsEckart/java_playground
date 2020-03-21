package lars.refactoring.preservewholeobject;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RoomTest {

  @Test
  void measured_temperature_within_heating_plan() {
    var room = aRoomWithNextMeasuredTemperature(6);
    var heatingPlan = new HeatingPlan(new TemperatureRange(5, 10));

    room.measureTemperature();

    assertThat(room.isWithinPlan(heatingPlan)).isTrue();
  }

  @NotNull
  private Room aRoomWithNextMeasuredTemperature(int i) {
    return new Room(new TemperatureRange(8, 8), () -> i);
  }

  @Test
  void measured_temperature_below_heating_plan() {
    Room room = aRoomWithNextMeasuredTemperature(4);

    room.measureTemperature();

    assertThat(room.isWithinPlan(new HeatingPlan(new TemperatureRange(5, 10)))).isFalse();
  }

  @Test
  void measured_temperature_above_heating_plan() {
    Room room = aRoomWithNextMeasuredTemperature(11);

    room.measureTemperature();

    assertThat(room.isWithinPlan(new HeatingPlan(new TemperatureRange(5, 10)))).isFalse();
  }
}
