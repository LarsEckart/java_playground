package lars.katas.soundex;

import java.util.Map;

class Soundex {

  public static final int MAX_CODE_LENGTH = 4;
  public static final String NOT_A_DIGIT = "";

  private static final Map<Character, String> encodings =
      Map.ofEntries(
          Map.entry('b', "1"),
          Map.entry('f', "1"),
          Map.entry('p', "1"),
          Map.entry('v', "1"),
          Map.entry('c', "2"),
          Map.entry('g', "2"),
          Map.entry('j', "2"),
          Map.entry('k', "2"),
          Map.entry('q', "2"),
          Map.entry('s', "2"),
          Map.entry('x', "2"),
          Map.entry('z', "2"),
          Map.entry('d', "3"),
          Map.entry('t', "3"),
          Map.entry('l', "4"),
          Map.entry('n', "5"),
          Map.entry('m', "5"),
          Map.entry('r', "6"));

  public String encode(String word) {
    String encoded = upperFront(head(word)) + encodeDigits(tail(word));
    return zeroPad(encoded);
  }

  private String upperFront(String head) {
    return head.toUpperCase();
  }

  private String head(String word) {
    return word.substring(0, 1);
  }

  private String tail(String word) {
    return word.substring(1);
  }

  private String encodeDigits(String word) {
    String result = NOT_A_DIGIT;
    for (char letter : word.toCharArray()) {
      if (isComplete(result)) {
        break;
      }
      String digit = encodedDigit(lower(letter));
      if (!digit.isEmpty() && !digit.equals(lastDigit(result))) {
        result += digit;
      }
    }
    return result;
  }

  private char lower(char letter) {
    return Character.toLowerCase(letter);
  }

  private String lastDigit(String encoding) {
    if (encoding.isEmpty()) {
      return NOT_A_DIGIT;
    }
    return encoding.substring(encoding.length() - 1);
  }

  private boolean isComplete(String result) {
    return result.length() == MAX_CODE_LENGTH - 1;
  }

  public String encodedDigit(char letter) {
    System.out.println("look up for " + letter);
    return encodings.getOrDefault(letter, NOT_A_DIGIT);
  }

  private String zeroPad(String word) {
    var zeroesNeeded = MAX_CODE_LENGTH - word.length();
    return word + "0".repeat(zeroesNeeded);
  }
}
