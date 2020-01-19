package lars.spielplatz;

public class Conditionals {

  // Let's say that I want to check that something is between 5 and 10.
  public static void main(String[] args) {
    int x = Integer.parseInt(args[0]);
    if (x > 5 && 10 > x) {}

    if (5 < x && 10 > x) {}

    if (x > 5 && x < 10) {}

    if (10 < x && x < 5) {}

    if (x < 10 && x > 5) {}

    if (x < 10 && 5 < x) {}

    if (5 < x && x < 10) {} // best
  }
}
