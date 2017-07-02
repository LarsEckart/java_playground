package de.larseckart.spielplatz;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        /*

        final Integer[] numbers = IntStream.rangeClosed(1, 10_000).boxed().toArray(Integer[]::new);

        List<String> values = Arrays.asList("A", "B", "C");
        System.out.println(values);
        calculateResults(values);
        System.out.println(values);

        */

        try {
            int number = Integer.valueOf("abc");
            System.out.println(number);
        } catch (NumberFormatException ex) {
            System.out.println("you idiot");
        }
    }

    private static void calculateResults(List<String> values) {
        values.removeIf(s -> s.length() < 2);
    }
}