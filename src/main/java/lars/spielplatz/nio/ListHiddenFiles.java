package lars.spielplatz.nio;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ListHiddenFiles {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("usage: java ListHiddenFiles directory");
            return;
        }

        Path dir = Paths.get(args[0]);
        DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
        for (Path file : stream)
            if (Files.isHidden(file)) {
                System.out.println(file);
            }
    }
}
