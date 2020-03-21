package lars.spielplatz;

public class AnonymousClass {

  public static void main(String[] args) {
    new Object() {
      private void test() {
        System.out.println("anonymous test");
      }
    }.test();

    var obj =
        new Object() {
          private void test() {
            System.out.println("anonymous test");
          }
        };
    obj.test();
  }
}
