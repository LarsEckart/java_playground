package lars.spielplatz.java8;

import java.util.function.IntSupplier;

public class LambdaThings {

  public static void main(String[] args) {
    lazyLambda();
    explodingMethodReference();
  }

  public static void lazyLambda() {
    String s = null;
    IntSupplier i = () -> s.length();
  }

  public static void explodingMethodReference() {
    String s = null;
    IntSupplier i = s::length;
  }
}
