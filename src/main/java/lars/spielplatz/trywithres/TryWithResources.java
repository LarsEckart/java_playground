package lars.spielplatz.trywithres;

/** from http://marxsoftware.blogspot.com/2018/08/multiple-resources-try-with-resources.html */
public class TryWithResources {

  public static void main(String[] args) {
    incorrect();
    System.out.println("---");
    correct();
  }

  static void incorrect() {
    try (OuterResource outer =
        new OuterResource(new InnerResource(), new RuntimeException("OUTER"))) {
      System.out.println(outer);
    } catch (Exception exception) {
      System.out.println("ERROR: " + exception);
    }
  }

  static void correct() {
    try (InnerResource inner = new InnerResource();
        OuterResource outer = new OuterResource(inner, new RuntimeException("OUTER"))) {
      System.out.println(outer);
    } catch (Exception exception) {
      System.out.println("ERROR: " + exception);
    }
  }
}
