package lars.blog;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

class CsvFileAnalysis {

  public static void main(String[] args) {
    Path workingDir = Path.of("src/main/resources");
    Path p = workingDir.resolve("friends.csv");

    try (Stream<String> lines = Files.lines(p)) {
      lines
          .filter(l -> !l.startsWith("#") || l.isEmpty())
          .map(CsvFileAnalysis::lineToPerson)
          .forEach(System.out::println);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Person lineToPerson(String line) {
    String[] elements = line.split(",");
    String name = elements[0];
    int age = Integer.parseInt(elements[1]);
    return new Person(name, age);
  }

  record Person(String name, int age) {}
}
