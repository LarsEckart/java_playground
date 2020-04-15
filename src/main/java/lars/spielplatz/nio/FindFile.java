package lars.spielplatz.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

class FindFile {

    public static void main(String[] args) throws IOException {


        // files touched in last 2hour
        Path myJvaPlayground = Paths.get("~/github/java_playground");
        Instant twoHoursAgo = Instant.now().minus(Duration.ofHours(2));
        try (Stream<Path> pathStream = Files.find(myJvaPlayground, 50,
                (path, attributes) -> attributes.creationTime().toInstant().isAfter(twoHoursAgo))) {
            pathStream.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
        }
    }
}
