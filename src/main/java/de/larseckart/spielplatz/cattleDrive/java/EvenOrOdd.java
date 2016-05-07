package de.larseckart.spielplatz.cattleDrive.java;

public class EvenOrOdd {

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                int providedNumber = Integer.parseInt(args[0]);
                if (providedNumber % 2 == 0) {
                    System.out.println("even");
                } else {
                    System.out.println("odd");
                }
            } catch (NumberFormatException exception) {
                System.out.println("Input was not a number");
            }
        }
    }
}
