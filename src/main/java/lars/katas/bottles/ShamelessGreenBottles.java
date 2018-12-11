package lars.katas.bottles;

public class ShamelessGreenBottles extends Bottles {

    @Override
    public String verse(int number) {
        switch (number) {
            case 0:
                return "No more bottles of beer on the wall, " +
                        "no more bottles of beer.\n" +
                        "Go to the store and buy some more, " +
                        "99 bottles of beer on the wall.\n";
            default:
                return number + " " + container(number) + " of beer on the wall, " +
                        number + " " + container(number) + " of beer.\n" +
                        "Take " + pronoun(number) + " down and pass it around, " +
                        quantity(number - 1) + " " + container(number - 1) + " of beer on the wall.\n";
        }
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
}
