package lars.spielplatz.nio;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ListHiddenFiles {

  private static Path validatePath(String pathStr) throws IOException {
    Path path = Paths.get(pathStr).normalize();
    if (path.isAbsolute()) {
      throw new SecurityException("Absolute paths are not allowed");
    }
    if (path.toString().contains("..")) {
      throw new SecurityException("Path traversal attempts are not allowed");
    }
    return path;
  }

  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      System.err.println("usage: java ListHiddenFiles directory");
      return;
    }

    Path dir;
    try {
      dir = validatePath(args[0]);
    } catch (SecurityException e) {
      System.err.printf("Invalid path: %s%n", e.getMessage());
      return;
    }
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
      for (Path file : stream) {
        if (Files.isHidden(file)) {
          System.out.println(file);
        }
      }
    }
  }
}
