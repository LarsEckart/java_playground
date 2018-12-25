package lars.advent2018;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class Day1 {

    private int frequency(String input) {
        return parseNumbers(input).sum();
    }

    private IntStream parseNumbers(String input) {
        return Arrays.stream(input.split(System.lineSeparator()))
                .map(String::strip)
                .mapToInt(Integer::parseInt);
    }

    @Nested
    class Part1 {

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
    }

    private int firstFrequencyTwice(String input) {
        List<Integer> split = parseNumbers(input).boxed().collect(toList());
        int frequency = 0;
        var frequencies = new HashSet<Integer>();
        frequencies.add(frequency);
        while (true) {
            for (Integer i : split) {
                frequency = frequency + i;
                if (frequencies.contains(frequency)) {
                    return frequency;
                }

                frequencies.add(frequency);
            }
        }
    }

    @Nested
    class Part2 {

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

        @Test
        void my_numbers() throws Exception {
            String allNumbers = Files.readString(new File("src/test/resources/advent2018/day1.txt").toPath());
            assertThat(firstFrequencyTwice(allNumbers)).isEqualTo(83445);
        }
    }
}
