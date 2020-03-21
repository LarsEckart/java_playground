package lars.cracking.chap1;

public class Urlify {

  public String urlify(String text, int length) {
    final int spaces = Math.toIntExact(text.chars().filter(c -> c == ' ').count());
    char[] url = new char[length + 2 * spaces];
    int urlIndex = url.length - 1;

    final char[] chars = text.toCharArray();
    for (int i = chars.length - 1; i >= 0; i--) {
      if (chars[i] == ' ') {
        url[urlIndex] = '0';
        url[urlIndex - 1] = '2';
        url[urlIndex - 2] = '%';
        urlIndex = urlIndex - 3;
      } else {
        url[urlIndex] = chars[i];
        urlIndex--;
      }
    }
    return new String(url);
  }
}
