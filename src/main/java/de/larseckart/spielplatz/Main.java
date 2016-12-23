package de.larseckart.spielplatz;

import java.util.Objects;

public class Main {

    public static void main(String args[]) {
        System.out.println("hello world");
        new Main().doStuff(null, "");
    }

    public void doStuff(String first, String second) {
        Objects.requireNonNull(first);
    }

}