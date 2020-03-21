package lars.spielplatz.java8;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class KaliChronia {

  public static void main(String[] args) {
    Stream<LocalDate> newYearDays =
        StreamSupport.stream(new YearSpliterator(LocalDate.of(2018, Month.JANUARY, 1)), false);

    newYearDays
        .filter(day -> day.getDayOfWeek() == DayOfWeek.MONDAY)
        .takeWhile(day -> day.getYear() >= 1900)
        .forEach(System.out::println);

    System.out.println("week days of my birthday");

    StreamSupport.stream(new YearSpliterator(LocalDate.of(2018, Month.AUGUST, 6)), false)
        .takeWhile(day -> day.getYear() >= 1985)
        .map(day -> day + " -> " + day.getDayOfWeek())
        .forEach(System.out::println);
  }
}
