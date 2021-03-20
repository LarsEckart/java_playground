package lars.spielplatz.java16;

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
}
