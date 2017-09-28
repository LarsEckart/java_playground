package ee.lars.kata;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringCalculator {

    private static final String DEFAULT_DELIMITER = ",";
    private static final String CUSTOM_DELIMITER_INDICATOR = "//";

    public int add(String numbers) {
        if (numbers != null && numbers.isEmpty()) {
            return 0;
        } else {
            return this.parseAndSum(numbers);
        }
    }

    private int parseAndSum(String numbers) {
        List<Integer> numbersList = parseInputString(numbers);
        return sum(numbersList);
    }

    private List<Integer> parseInputString(String numbers) {
        Delimiter delimiter = determineDelimiter(numbers);
        return extractNumbers(numbers, delimiter);
    }

    private Delimiter determineDelimiter(String numbers) {
        if (numbers.startsWith(CUSTOM_DELIMITER_INDICATOR)) {
            final String customDelimiter =
                    numbers.substring(CUSTOM_DELIMITER_INDICATOR.length(), numbers.indexOf("\n"));
            if (customDelimiter.length() > 1) {
                return Delimiter.customDelimiter(customDelimiter.substring(1, customDelimiter.length() - 1));
            } else {
                return Delimiter.customDelimiter(customDelimiter);
            }
        } else {
            return Delimiter.defaultDelimiter();
        }
    }

    private List<Integer> extractNumbers(String numbers, Delimiter delimiter) {
        String numbersToParse = numbers;
        if (delimiter.isCustom()) {
            int numbersStart = numbers.indexOf("\n") + 1;
            numbersToParse = numbersToParse.substring(numbersStart, numbersToParse.length());
        }

        if (numbersToParse.contains("\n")) {
            numbersToParse = numbersToParse.replace("\n", delimiter.get());
        }
        return Arrays.asList(numbersToParse.split(delimiter.get()))
                     .stream()
                     .mapToInt(Integer::parseInt)
                     .boxed()
                     .collect(Collectors.toList());
    }

    private int sum(List<Integer> numbers) {
        checkListContainsOnlyPositiveNumbers(numbers);

        final int sum = numbers.stream()
                               .filter(n -> n < 1000)
                               .reduce(0, (a, b) -> a + b);

        return sum;
    }

    private void checkListContainsOnlyPositiveNumbers(List<Integer> numbers) {
        final List<Integer> negatives = numbers.stream()
                                               .filter(n -> n < 0)
                                               .collect(Collectors.toList());

        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException("negatives not allowed: " + negatives.toString());
        }
    }

    private static class Delimiter {

        private final String token;

        Delimiter(String token) {
            this.token = token;
        }

        static Delimiter customDelimiter(String token) {
            return new Delimiter(token);
        }

        static Delimiter defaultDelimiter() {
            return new Delimiter(DEFAULT_DELIMITER);
        }

        boolean isCustom() {
            return !DEFAULT_DELIMITER.equals(this.token);
        }

        String get() {
            return this.token;
        }
    }
}
