package de.larseckart.spielplatz.cattleDrive.java;

import java.util.Calendar;

public class Leap {

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                int year = Integer.parseInt(args[0]);
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                if (cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365) {
                    System.out.println("leap year!");
                } else {
                    System.out.println("not a leap year!");
                }
            } catch (NumberFormatException exception) {
                System.out.println("input was not a number");
            }
        }
    }
}
