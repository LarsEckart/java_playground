package lars.advent2018;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class Day1 {

    private int frequency(String input) {
        int[] numbers = parseNumbers(input);
        return Arrays.stream(numbers).sum();
    }

    private int[] parseNumbers(String input) {
        if (!input.contains(System.lineSeparator())) {
            return new int[] {Integer.parseInt(input)};
        }

        return Arrays.stream(input.split(System.lineSeparator()))
                .map(String::strip)
                .mapToInt(Integer::parseInt).toArray();
    }

    @Test
    void parse_single_positive_change_input() {
        var input = "+1";

        assertThat(frequency(input)).isEqualTo(1);
    }

    @Test
    void parse_single_negative_change_input() {
        var input = "-1";

        assertThat(frequency(input)).isEqualTo(-1);
    }

    @Test
    void parse_multiple_positive_change_inputs() {
        var input = "+1\n+3";

        assertThat(frequency(input)).isEqualTo(4);
    }

    @Test
    void parse_multiple_negative_change_inputs() {
        var input = "-1\n-2";

        assertThat(frequency(input)).isEqualTo(-3);
    }

    @Test
    void parse_positive_and_negative_frequency_change_inputs() {
        var input = "+1\n-2\n+3\n+1";

        assertThat(frequency(input)).isEqualTo(3);
    }

    private int firstFrequencyTwice(String input) {
        int[] split = parseNumbers(input);
        int currentFrequency = 0;
        var frequencies = new ArrayList<Integer>();
        frequencies.add(currentFrequency);
        int endlessLoopStop = 0;
        while (endlessLoopStop < 500) {
            for (int i = 0; i < split.length; ) {
                var newFrequency = currentFrequency + (split[i]);
                if (frequencies.contains(newFrequency)) {
                    System.out.println(i);
                    return newFrequency;
                }

                frequencies.add(newFrequency);
                currentFrequency = newFrequency;

                if (i == split.length - 1) {
                    i = 0;
                } else {
                    i++;
                }
            }
            endlessLoopStop++;
        }
        throw new RuntimeException("more than 500 loops through the input to find detection.");
    }

    @Test
    void detects_simple_repetition() {
        var input = "1\n-1";

        assertThat(firstFrequencyTwice(input)).isEqualTo(0);
    }

    @Test
    void detects_repetition() {
        var input = "+3\n+3\n+4\n-2\n-4";

        assertThat(firstFrequencyTwice(input)).isEqualTo(10);
    }
}
