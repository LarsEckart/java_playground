package lars.spielplatz.java8;

import java.util.Optional;

public class EverythingOptional {

  public static void main(String[] args) {

    Optional<String> optional = Optional.of("Lars on tubli poiss");

    String result = optional.orElse(getName());
    // String result = optional.orElseGet(() -> getName());

    System.out.println(result);
  }

  private static String getName() {
    System.out.println("computing");
    return "text";
  }

  private static Authentication findAuthentication(long id) {
    return new Authentication();
  }

  private Optional<Authentication> fixndAuthentication(long id) {
    return Optional.empty();
  }

  static class Authentication {

    public String getStatus() {
      return "";
    }
  }
}
