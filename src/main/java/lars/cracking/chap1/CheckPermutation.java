package lars.cracking.chap1;

import java.util.Arrays;

public class CheckPermutation {

    public static boolean isPermutation(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        } else {
            final char[] firstChars = first.toCharArray();
            final char[] secondChars = second.toCharArray();
            Arrays.sort(firstChars);
            Arrays.sort(secondChars);
            return Arrays.equals(firstChars, secondChars);
        }
    }
}
