package ee.lars.katas.second;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringCalculator {

    private static final String DEFAULT_SEPARATOR = ",";
    private static final int CUSTOM_SEPARATOR_DEFINITION = 4;

    public int add(String rawInput) {
        if (rawInput.isEmpty()) {
            return 0;
        } else if (rawInput.length() == 1) {
            return Integer.parseInt(rawInput);
        } else {
            String numbers = rawInput;
            String separator = DEFAULT_SEPARATOR;
            if (hasCustomSeparator(rawInput)) {
                separator = determineSeparator(rawInput);
                numbers = extractNumbersPart(rawInput);
            }
            String normalizedNumbers = numbers.replace("\n", separator);
            String[] split = normalizedNumbers.split(separator);
            disallowNegativeNumbers(split);
            return Arrays.stream(split)
                    .mapToInt(Integer::parseInt)
                    .filter(n -> n < 1000)
                    .sum();
        }
    }

    private boolean hasCustomSeparator(String rawInput) {
        return rawInput.startsWith("//");
    }

    private String determineSeparator(String numbers) {
        if (numbers.startsWith("//")) {
            return numbers.substring(2, 3);
        } else {
            return DEFAULT_SEPARATOR;
        }
    }

    private String extractNumbersPart(String rawInput) {
        return rawInput.substring(CUSTOM_SEPARATOR_DEFINITION, rawInput.length());
    }

    private void disallowNegativeNumbers(String[] split) {
        List<Integer> negativeNumbers = Arrays.stream(split)
                .mapToInt(Integer::parseInt)
                .filter(n -> n < 0)
                .boxed()
                .collect(Collectors.toList());
        if (!negativeNumbers.isEmpty()) {
            throw new IllegalArgumentException("Input contains negative numbers: " + negativeNumbers);
        }
    }
}
