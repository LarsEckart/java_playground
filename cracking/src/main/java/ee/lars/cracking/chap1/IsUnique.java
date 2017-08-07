package ee.lars.cracking.chap1;

public class IsUnique {

    public boolean isUnique(String text) {
        if (text.length() == 1) {
            return true;
        } else {
            return false;
        }
    }
}