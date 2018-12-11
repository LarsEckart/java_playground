package lars.katas.bottles;

public class ShamelessGreenBottles extends Bottles {

    @Override
    public String verse(int number) {
        return capitalize(quantity(number)) + " " + container(number) + " of beer on the wall, " +
                quantity(number) + " " + container(number) + " of beer.\n" +
                action(number) + ", " +
                quantity(successor(number)) + " " + container(successor(number)) + " of beer on the wall.\n";
    }

    private int successor(int number) {
        return number == 0 ? 99 : number - 1;
    }

    private String container(int number) {
        return number == 1 ? "bottle" : "bottles";
    }

    private String pronoun(int number) {
        return number == 1 ? "it" : "one";
    }

    private String quantity(int number) {
        return number == 0 ? "no more" : String.valueOf(number);
    }

    private String action(int number) {
        return number == 0 ? "Go to the store and buy some more" : "Take " + pronoun(number) + " down and pass it around";
    }

    private String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
