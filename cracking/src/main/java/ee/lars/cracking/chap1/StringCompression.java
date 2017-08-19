package ee.lars.cracking.chap1;

public class StringCompression {

    public static String compress(String text) {
        if (text.length() == 1) {
            return text;
        }

        StringBuilder strb = new StringBuilder();
        final char[] chars = text.toCharArray();
        int amount = 1;

        for (int i = 0; i < chars.length; i++) {
            if (i == chars.length - 1) {
                strb.append(chars[i]);
                strb.append(amount);
                amount = 1;
            } else {
                if (chars[i] == chars[i + 1]) {
                    amount++;
                } else {
                    strb.append(chars[i]);
                    strb.append(amount);
                    amount = 1;
                }
            }
        }

        String compressed = strb.toString();

        return (compressed.length() < text.length()) ? compressed : text;
    }
}
