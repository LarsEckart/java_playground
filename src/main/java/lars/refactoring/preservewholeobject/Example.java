package lars.refactoring.preservewholeobject;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

class Example {

  /**
   * Consider a room object that records high and low temperatures during the day. It needs to
   * compare this range with a range in a predefined heating plan:
   */
  public static void main(String[] args) {
    var baseline = new TemperatureRange(22, 22);

    var temperatures = new ConcurrentLinkedDeque<>(List.of(20, 20, 21, 22, 23, 24, 22, 22));
    var room = new Room(baseline, new TemperatureSensor(temperatures));
    var plan = new HeatingPlan(new TemperatureRange(20, 22));

    temperatures.forEach((t) -> room.measureTemperature());
    // simulation finished

    if (!room.isWithinPlan(plan)) {
      System.out.println(
          "\nalert: today's room temperatures "
              + room.getTodaysTemperatureRange()
              + " outside of "
              + plan);
    } else {
      System.out.println("all good");
    }
  }
}
