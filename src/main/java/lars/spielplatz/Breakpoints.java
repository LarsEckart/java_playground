package lars.spielplatz;

public class Breakpoints {

  public static void main(String[] args) {

    calcAge(11);
    calcAge(22);
    calcAge(33); // set breakpoint but uncheck "Suspended"
  }

  static int calcAge(int any) {
    System.out.println("calc");
    System.out.println("calc");
    System.out.println(
        "calc"); // set breakpoint but check "disable until hitting following breakpoint"
    return 42;
  }
}
