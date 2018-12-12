package lars.katas.bottles;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OpenBottles extends Bottles {

    public String song() {
        return verses(99, 0);
    }

    public String verses(int start, int end) {
        return Stream.iterate(start, i -> i >= end, i -> i - 1)
                .map(this::verse)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String verse(int number) {
        BottleNumber bottleNumber = new BottleNumber(number);
        BottleNumber nextBottleNumber = bottleNumber.successor();

        return capitalize(bottleNumber + " of beer on the wall, ") +
                bottleNumber + " of beer.\n" +
                bottleNumber.action() + ", " +
                nextBottleNumber + " of beer on the wall.\n";
    }

    private String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    class BottleNumber {

        private int number;

        public BottleNumber(int number) {
            this.number = number;
        }

        private BottleNumber successor() {
            return this.number == 0 ? new BottleNumber(99) : new BottleNumber(number - 1);
        }

        private String container() {
            return number == 1 ? "bottle" : "bottles";
        }

        private String pronoun() {
            return number == 1 ? "it" : "one";
        }

        private String quantity() {
            return number == 0 ? "no more" : String.valueOf(number);
        }

        private String action() {
            return number == 0 ? "Go to the store and buy some more" : "Take " + pronoun() + " down and pass it around";
        }

        @Override
        public String toString() {
            return quantity() + " " + container();
        }
    }
}
