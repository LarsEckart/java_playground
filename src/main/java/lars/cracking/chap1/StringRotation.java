package lars.cracking.chap1;

public class StringRotation {

  /**
   * Given two strings, S1 and S2, write code to check if S2 is a rotation of S1 using only one call
   * to isSubstring (e.g.,"waterbottle"is a rotation of"erbottlewat").
   */
  public static boolean isRotation(String original, String rotation) {
    String rotatedTwice = rotation + rotation;
    return rotatedTwice.contains(original);
  }
}
