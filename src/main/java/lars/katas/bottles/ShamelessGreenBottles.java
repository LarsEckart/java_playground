package lars.katas.bottles;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShamelessGreenBottles extends Bottles {

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
        return capitalize(quantity(number)) + " " + container(number) + " of beer on the wall, " +
                quantity(number) + " " + container(number) + " of beer.\n" +
                action(number) + ", " +
                quantity(successor(number)) + " " + container(successor(number)) + " of beer on the wall.\n";
    }

    private int successor(int number) {
        return new BottleNumber(number).successor();
    }

    private String container(int number) {
        return new BottleNumber(number).container();
    }

    private String quantity(int number) {
        return new BottleNumber(number).quantity();
    }

    private String action(int number) {
        return new BottleNumber(number).action();
    }

    private String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    class BottleNumber {

        private int number;

        public BottleNumber(int number) {
            this.number = number;
        }

        private int successor() {
            return this.number == 0 ? 99 : number - 1;
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
    }
}
