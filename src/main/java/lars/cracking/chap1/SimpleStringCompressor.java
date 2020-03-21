package lars.cracking.chap1;

public class SimpleStringCompressor {

  private static final int ONE = 1;

  public static String compress(String text) {
    if (text.length() <= 1) {
      return text;
    }

    StringBuilder strb = new StringBuilder();
    final char[] chars = text.toCharArray();
    int amount = ONE;

    for (int i = 0; i < chars.length; i++) {
      if (isLastCharacter(chars, i)) {
        compress(strb, chars[i], amount);
        amount = ONE;
      } else {
        if (currentCharacterIsFollowedBySameCharacter(chars, i)) {
          amount++;
        } else {
          compress(strb, chars[i], amount);
          amount = ONE;
        }
      }
    }

    String compressed = strb.toString();

    return (compressed.length() < text.length()) ? compressed : text;
  }

  private static boolean isLastCharacter(char[] chars, int i) {
    return i == chars.length - 1;
  }

  private static boolean currentCharacterIsFollowedBySameCharacter(char[] chars, int i) {
    return chars[i] == chars[i + 1];
  }

  private static void compress(StringBuilder strb, char currentChar, int amount) {
    strb.append(currentChar);
    strb.append(amount);
  }
}
