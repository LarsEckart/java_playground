package lars.spielplatz.java12;

import java.text.NumberFormat;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CompactNumberFormatting {

  @Test
  void format_numbers_when_space_is_limited() {
    NumberFormat nf = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);

    assertThat(nf.format(10000)).isEqualTo("10K");
    assertThat(nf.format(120300)).isEqualTo("120K");
    assertThat(nf.format(2120000)).isEqualTo("2M");
    assertThat(nf.format(1950000300)).isEqualTo("2B");

    nf = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.LONG);

    assertThat(nf.format(10000)).isEqualTo("10 thousand");
    assertThat(nf.format(120300)).isEqualTo("120 thousand");
    assertThat(nf.format(2120000)).isEqualTo("2 million");
    assertThat(nf.format(1950000300)).isEqualTo("2 billion");

    NumberFormat germanShort =
        NumberFormat.getCompactNumberInstance(Locale.GERMANY, NumberFormat.Style.SHORT);

    assertThat(germanShort.format(10000)).isEqualTo("10.000");
    assertThat(germanShort.format(120300)).isEqualTo("120.300");
    assertThat(germanShort.format(2120000)).isEqualTo("2 Mio.");
    assertThat(germanShort.format(1950000300)).isEqualTo("2 Mrd.");

    NumberFormat germanLong = NumberFormat.getCompactNumberInstance(Locale.GERMANY, NumberFormat.Style.LONG);

    assertAll(
        () -> assertThat(germanLong.format(10000)).isEqualTo("10 Tausend"),
        () -> assertThat(germanLong.format(120300)).isEqualTo("120 Tausend"),
        // TODO: bug with locales? passes locally with zulu-13 but fails on github&travis
        () -> assertThat(germanLong.format(1120000)).isEqualTo("1 Millionen"),
        () -> assertThat(germanLong.format(2120000)).isEqualTo("2 Millionen"),
        () -> assertThat(germanLong.format(1150000300)).isEqualTo("1 Milliarde"),
        () -> assertThat(germanLong.format(1950000300)).isEqualTo("2 Milliarden"));
  }
}
