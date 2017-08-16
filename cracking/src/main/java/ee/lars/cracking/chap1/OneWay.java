package ee.lars.cracking.chap1;

public class OneWay {

    public boolean oneEditAway(String first, String second) {
        if (Math.abs(first.length() - second.length()) > 1) {
            return false;
        } else {
            if (new CheckPermutation().isPermutation(first, second)) {
                return true;
            } else {
                return false;
            }
        }
    }
}
