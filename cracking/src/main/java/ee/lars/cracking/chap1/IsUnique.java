package ee.lars.cracking.chap1;

public class IsUnique {

    public boolean isUnique(String text) {
        if (text.length() == 1) {
            return true;
        } else if (text.length() == 2) {
            char[] chars = text.toCharArray();
            return chars[0] != chars[1];
        } else {
            long distinctChars = text.chars().distinct().count();
            return distinctChars == text.length();
        }
    }
}