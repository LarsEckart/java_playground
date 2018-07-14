package lars.cracking.chap1;

public class OneAway {

    public static boolean oneEditAway(String first, String second) {
        int lengthDifference = Math.abs(first.length() - second.length());

        switch (lengthDifference) {
            case 0:
                return replaceForMatch(first, second);
            case 1:
                return removeFromLonger(first, second);
            default:
                return false;
        }
    }

    /**
     * Replace each of first's characters with each of second's characters and figure out if the words then match.
     * bale balo
     * -> bale aale lale oale
     * -> bble bale blle bole
     * -> babe baae bale baoe
     * -> balb bala ball balo
     */
    private static boolean replaceForMatch(String first, String second) {
        final char[] secondChars = second.toCharArray();
        for (int i = 0; i < first.length(); i++) {
            StringBuilder strb = new StringBuilder(first);
            for (int j = 0; j < secondChars.length; j++) {
                strb.setCharAt(i, secondChars[j]);
                if (strb.toString().equals(second)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean removeFromLonger(String first, String second) {
        String shorter = first.length() < second.length() ? first : second;
        String longer = first.length() < second.length() ? second : first;
        for (int i = 0; i < longer.length(); i++) {
            StringBuilder strb = new StringBuilder(longer);
            strb.deleteCharAt(i);
            if (strb.toString().equals(shorter)) {
                return true;
            }
        }
        return false;
    }
}

