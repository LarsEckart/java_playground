package lars.refactoring;

class Pattern {

  public static String formatText(String text)
  {
    StringBuffer result = new StringBuffer();
    for (int n = 0; n < text.length(); ++n) {
      int c = text.charAt(n);
      if (c == '<') {
        while(n < text.length() && text.charAt(n) != '/' && text.charAt(n) != '>') {
          n++;
        }
        if (n < text.length() && text.charAt(n) == '/') {
          n+=4;
        } else {
          n++;
        }
      }
      if (n < text.length()) {
        result.append(text.charAt(n));
      }
    }
    return new String(result);
  }

}
