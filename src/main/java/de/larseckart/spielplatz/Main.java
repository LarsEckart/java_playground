package de.larseckart.spielplatz;

import java.util.LinkedList;
import java.util.Spliterator;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        final Integer[] numbers = IntStream.rangeClosed(1, 10_000).boxed().toArray(Integer[]::new);

        final Spliterator<String> spliterator = new LinkedList<String>().spliterator();
        System.out.println(spliterator.hasCharacteristics(Spliterator.CONCURRENT));
    }
}