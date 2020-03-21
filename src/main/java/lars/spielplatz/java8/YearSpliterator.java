package lars.spielplatz.java8;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class YearSpliterator implements Spliterator<LocalDate> {

  private LocalDate date;

  public YearSpliterator(LocalDate startDate) {
    this.date = startDate;
  }

  @Override
  public boolean tryAdvance(Consumer<? super LocalDate> action) {
    Objects.requireNonNull(action);
    action.accept(date);
    date = date.minusYears(1);
    return true;
  }

  @Override
  public Spliterator<LocalDate> trySplit() {
    return null;
  }

  @Override
  public long estimateSize() {
    return Long.MAX_VALUE;
  }

  @Override
  public int characteristics() {
    return DISTINCT | IMMUTABLE | NONNULL | ORDERED | SORTED;
  }

  @Override
  public Comparator<? super LocalDate> getComparator() {
    return Comparator.reverseOrder();
  }

  public static void main(String... args) {
    Stream<LocalDate> newYearDays =
        StreamSupport.stream(new YearSpliterator(LocalDate.of(2018, Month.JANUARY, 1)), false);
    newYearDays
        .filter(day -> day.getDayOfWeek() == DayOfWeek.MONDAY)
        .takeWhile(day -> day.getYear() >= 1900) // Java 9
        .forEach(System.out::println);
  }
}
