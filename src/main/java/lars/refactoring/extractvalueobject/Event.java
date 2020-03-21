package lars.refactoring.extractvalueobject;

import java.time.LocalDate;
import java.util.Objects;

class Event {

  private final String name;
  private final LocalDate from;
  private final LocalDate to;

  public static Event of(String name, LocalDate from, LocalDate to) {
    return new Event(name, from, to);
  }

  private Event(String name, LocalDate from, LocalDate to) {
    Objects.requireNonNull(name);
    Objects.requireNonNull(from);
    Objects.requireNonNull(to);

    if (to.isBefore(from)) {
      throw new IllegalArgumentException("to date before from date");
    }

    this.name = name;
    this.from = from;
    this.to = to;
  }

  String getName() {
    return name;
  }

  LocalDate getFrom() {
    return from;
  }

  LocalDate getTo() {
    return to;
  }
}
