package lars.katas.bottles;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConciseBottles extends Bottles {

    @Override
    public String verse(int n) {
        StringBuilder strb = new StringBuilder();

        strb.append(n == 0 ? "No more" : n);
        strb.append(n != 1 ? " bottles " : " bottle ");
        strb.append("of beer on the wall, ");
        strb.append(n == 0 ? "no more" : n);
        strb.append(n != 1 ? " bottles " : " bottle ");
        strb.append("of beer.");
        strb.append("\n");
        strb.append(n > 0 ? "Take " + (n > 1 ? "one" : "it") + " down and pass it around, "
                          : "Go to the store and buy some more, ");
        strb.append(n - 1 < 0 ? "99" : (n - 1 == 0 ? "no more" : String.valueOf(n - 1)));
        strb.append(n - 1 != 1 ? " bottles " : " bottle ");
        strb.append("of beer on the wall.\n");
        return strb.toString();
    }

    @Override
    public String verses(int start, int end) {
        return Stream.iterate(start, i -> i >= end, i -> i - 1)
                .map(this::verse)
                .collect(Collectors.joining("\n"));
    }
}
