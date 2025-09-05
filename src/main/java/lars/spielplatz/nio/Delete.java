package lars.spielplatz.nio;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Delete {

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

  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("usage: java Delete file-or-directory");
      return;
    }

    Path path;
    try {
      path = validatePath(args[0]);
    } catch (SecurityException | IOException e) {
      System.err.printf("Invalid path: %s%n", e.getMessage());
      return;
    }
    try {
      Files.delete(path);
    } catch (NoSuchFileException nsfe) {
      System.err.printf("%s: no such file or directory%n", path);
    } catch (DirectoryNotEmptyException dnee) {
      System.err.printf("%s: not empty%n", path);
    } catch (IOException ioe) {
      System.err.printf("I/O error: %s%n", ioe.getMessage());
    }
  }
}
