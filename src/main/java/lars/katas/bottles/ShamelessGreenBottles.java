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
            case 1:
                return number + " " + container(number) + " of beer on the wall, " +
                        number + " " + container(number) + " of beer.\n" +
                        "Take it down and pass it around, " +
                        "no more bottles of beer on the wall.\n";
            default:
                return number + " " + container(number) + " of beer on the wall, " +
                        number + " " + container(number) + " of beer.\n" +
                        "Take one down and pass it around, " +
                        (number - 1) + " " + container(number - 1) + " of beer on the wall.\n";
        }
    }

    private String container(int number) {
        return number == 1 ? "bottle" : "bottles";
    }
}
