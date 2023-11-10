package lars.spielplatz.containers;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

class HashtableExamples {

  static class Person {

    private final String name;

    public Person(String name) {
      this.name = name;
    }

    @Override
    public int hashCode() {
      return 1;
    }

    @Override
    public boolean equals(Object o) {
      return (o instanceof Person p) && name.equals(p.name);
    }
  }

  public static void main(String[] args) {
    Map<Person, Object> hashtable = new Hashtable<>();
    hashtable.put(new Person("Lars"), "any");
    System.out.println(hashtable);
    System.out.println(hashtable.get(new Person("Oskar")));

    Map<Person, Object> map = new HashMap<>();
    map.put(new Person("Lars"), "any");
    System.out.println(map);
    System.out.println(map.get(new Person("Oskar")));

    LongAdder la = new LongAdder();
    la.increment();
  }
}
