package lars.katas.soundex;

import java.util.Map;

class Soundex {

  public static final int MAX_CODE_LENGTH = 4;

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
    String encoded = head(word) + encodeDigits(word);
    return zeroPad(encoded);
  }

  private String encodeDigits(String word) {
    if (word.length() > 1) {
      return encodedDigit(word.charAt(1));
    }
    return "";
  }

  private String encodedDigit(char letter) {
    return encodings.getOrDefault(letter, "");
  }

  private String head(String word) {
    return word.substring(0, 1);
  }

  private String zeroPad(String word) {
    var zeroesNeeded = MAX_CODE_LENGTH - word.length();
    return word + "0".repeat(zeroesNeeded);
  }
}
