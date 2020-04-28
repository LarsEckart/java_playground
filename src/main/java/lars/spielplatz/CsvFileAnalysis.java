package lars.spielplatz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

class CsvFileAnalysis {

  public static void main(String[] args) {
    Path workingDir = Path.of("src/main/resources");
    Path p = workingDir.resolve("data.csv");

    try (Stream<String> lines = Files.lines(p)) {
      lines
          .filter(l -> !l.startsWith("#"))
          .flatMap(CsvFileAnalysis::lineToPerson)
          .forEach(System.out::println);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Stream<Person> lineToPerson(String line) {
    try {
      String[] elements = line.split(",");
      String name = elements[0];
      int age = Integer.parseInt(elements[1]);
      String city = elements[2];
      Person p = new Person(name, age, city);
      return Stream.of(p);
    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
      return Stream.empty();
    }
  }

  record Person(String name, int age, String city) {}
}
