package lars.blog;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

class CsvFileAnalysisWithApache {

  private static CSVFormat csvFormat = CSVFormat.Builder.create().setHeader().build();

  public static void main(String[] args) throws IOException {
    parseFriends().forEach(System.out::println);
  }

  private static List<Person> parseFriends() throws IOException {
    var records = csvFormat.parse(new FileReader(csvFile()));
    return records.stream().map(r -> recordToPerson(r)).toList();
  }

  private static Person recordToPerson(CSVRecord csvRecord) {
    String name = csvRecord.get("name");
    int age = Integer.parseInt(csvRecord.get("age"));
    return new Person(name, age);
  }

  record Person(String name, int age) {

  }

  private static File csvFile() {
    Path workingDir = Path.of("src/main/resources");
    return workingDir.resolve("friends.csv").toFile();
  }
}
