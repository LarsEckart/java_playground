package lars.katas.bottles;

public class Bottles {

    public String verse(int n) {
        if (n == 0) {
            return "No more bottles of beer on the wall, no more bottles of beer.\n"
                    + "Go to the store and buy some more, 99 bottles of beer on the wall.\n";
        }

        String result = "";

        String bottles = n == 1 ? "bottle" : "bottles";
        result += String.format("%d %s of beer on the wall, %d %s of beer.\n", n, bottles, n, bottles);

        int afterBottleCount = n - 1;
        String afterBottles = n - 1 == 1 ? "bottle" : "bottles";

        if (afterBottleCount == 0) {
            result += "Take it down and pass it around, ";
            result += "no more bottles of beer on the wall.\n";
        } else {
            result += "Take one down and pass it around, ";
            result += String.format("%d %s of beer on the wall.\n", afterBottleCount, afterBottles);
        }
        return result;
    }

    public String verses(int start, int end) {

        String result = "";
        for (int i = start; i >= end; i--) {
            result += verse(i);
            if (i > end) {
                result += "\n";
            }
        }
        return result;
    }

    public String song() {
        return verses(99, 0);
    }
}
