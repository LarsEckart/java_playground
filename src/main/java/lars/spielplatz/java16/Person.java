package lars.spielplatz.java16;

import java.util.Objects;

record Person(String name, int age) {
  Person {
    if (age < 0) {
      throw new IllegalArgumentException();
    }
    instanceCounter++;
  }

  private static int instanceCounter;

  static int getInstanceCounter() {
    return instanceCounter;
  }

  @Override
  public boolean equals(Object o) {
    return (o instanceof Person p) && name.equalsIgnoreCase(p.name) && age == p.age;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, age);
  }
}
