package lars.spielplatz.java14;

import java.util.List;

public class HelpfulNullPointer {

  // -XX:+ShowCodeDetailsInExceptionMessages
  public static void main(String[] args) {
    Person person = new Person();

    System.out.println(person.hobbies.contains("programming"));
  }

  static class Person {
    String name;
    List<String> hobbies;
  }
}
