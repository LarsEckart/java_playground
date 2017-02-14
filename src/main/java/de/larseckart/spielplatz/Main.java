package de.larseckart.spielplatz;

import java.util.LinkedList;
import java.util.Spliterator;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        final Integer[] numbers = IntStream.rangeClosed(1, 10_000).boxed().toArray(Integer[]::new);

        final Spliterator<String> spliterator = new LinkedList<String>().spliterator();
        //System.out.println(spliterator.hasCharacteristics(Spliterator.CONCURRENT));

        int i = 2;
        int j = (i = 3) * i;
        System.out.println(j);

        int a = 9;
        a += (a = 3); // first example
        System.out.println(a);
        int b = 9;
        b = b + (b = 3); // second example
        System.out.println(b);
    }
}