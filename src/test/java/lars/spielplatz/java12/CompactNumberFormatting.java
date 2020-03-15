package lars.spielplatz.java12;

import java.text.NumberFormat;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompactNumberFormatting {

  @Test
  void name() {
    NumberFormat nf = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);

    assertThat(nf.format(10000)).isEqualTo("10K");
    assertThat(nf.format(120300)).isEqualTo("120K");
    assertThat(nf.format(2120000)).isEqualTo("2M");
    assertThat(nf.format(1950000300)).isEqualTo("2B");

    nf = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.LONG);

    assertThat( nf.format(10000)).isEqualTo("10 thousand");
    assertThat( nf.format(120300)).isEqualTo("120 thousand");
    assertThat( nf.format(2120000)).isEqualTo("2 million");
    assertThat( nf.format(1950000300)).isEqualTo("2 billion");

    NumberFormat german = NumberFormat.getCompactNumberInstance(Locale.GERMANY, NumberFormat.Style.SHORT);

    assertThat(german.format(10000)).isEqualTo("10.000");
    assertThat(german.format(120300)).isEqualTo("120.300");
    assertThat(german.format(2120000)).isEqualTo("2 Mio.");
    assertThat(german.format(1950000300)).isEqualTo("2 Mrd.");

    german = NumberFormat.getCompactNumberInstance(Locale.GERMANY, NumberFormat.Style.LONG);

    assertThat(german.format(10000)).isEqualTo("10 Tausend");
    assertThat(german.format(120300)).isEqualTo("120 Tausend");
    // TODO: azul zulu bug? locally succeeds only with "2 Million" but on github&travis it fails and claims must be "2 Millionen"
    //assertThat(german.format(2120000)).isEqualTo("2 Millionen");
    assertThat(german.format(1150000300)).isEqualTo("1 Milliarde");
    assertThat(german.format(1950000300)).isEqualTo("2 Milliarde");
  }
}
