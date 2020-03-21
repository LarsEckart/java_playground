package lars.spielplatz;

import java.util.List;
import java.util.Locale;

public class CompactNumberFormat {

  public static void main(String[] args) {
    var numbers = List.of(1_000, 1_000_000, 1_000_000_000);
    var locales =
        List.of(
            Locale.UK,
            new Locale("pl", "pl"),
            new Locale("uk", "ua"),
            new Locale("ru", "ru"),
            new Locale("fr", "be"),
            new Locale("nl", "be"),
            new Locale("de", "be"));
    /*
           locales.forEach(locale -> {
                       var shortFormatter = NumberFormat.getCompactNumberInstance(locale, NumberFormat.Style.SHORT);
                       var longFormatter = NumberFormat.getCompactNumberInstance(locale, NumberFormat.Style.LONG);
                       numbers.forEach(number -> {
                                   String formatSample = String.format(
                                           "In %-14s speaking %-9s %10d is [%-6s] [%-11s]",
                                           locale.getDisplayCountry(),
                                           locale.getDisplayLanguage(),
                                           number,
                                           shortFormatter.format(number),
                                           longFormatter.format(number));
                                   System.out.println(formatSample);
                               }
                       );
                   }
           );
    */
  }
}
