package lars.katas.soundex;

import java.util.Map;

class Soundex {

  public static final int MAX_CODE_LENGTH = 4;
  public static final String NOT_A_DIGIT = "*";

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
    return zeroPad(upperFront(head(word)) + tail(encodeDigits(word)));
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
    String result;
    result = encodeHead(word);
    result = encodeTail(word, result);
    return result;
  }

  private String encodeHead(String word) {
    return encodedDigit(word.charAt(0));
  }

  private String encodeTail(String word, String result) {
    char[] charArray = word.toCharArray();
    for (int i = 1; i < charArray.length; i++) {
      if (!isComplete(result)) {
        result = encodeLetter(result, charArray[i], charArray[i - 1]);
      }
    }
    return result;
  }

  private String encodeLetter(String result, char letter, char lastLetter) {
    String digit = encodedDigit(letter);
    if (!digit.equals(NOT_A_DIGIT) &&
        (!digit.equals(lastDigit(result)) || isVowel(lastLetter))) {
      result += digit;
    }
    return result;
  }

  private boolean isVowel(char letter) {
    return "aeiou".contains(String.valueOf(letter));
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
    return result.length() == MAX_CODE_LENGTH;
  }

  public String encodedDigit(char letter) {
    return encodings.getOrDefault(lower(letter), NOT_A_DIGIT);
  }

  private String zeroPad(String word) {
    var zeroesNeeded = MAX_CODE_LENGTH - word.length();
    return word + "0".repeat(zeroesNeeded);
  }
}
