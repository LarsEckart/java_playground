package lars.spielplatz.nio;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Move {

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
    if (args.length != 2) {
      System.err.println("usage: java Move source target");
      return;
    }

    Path source;
    Path target;
    try {
      source = validatePath(args[0]);
      target = validatePath(args[1]);
    } catch (SecurityException | IOException e) {
      System.err.printf("Invalid path: %s%n", e.getMessage());
      return;
    }

    try {
      Files.move(source, target);
    } catch (FileAlreadyExistsException faee) {
      System.err.printf("%s: file already exists%n", target);
    } catch (DirectoryNotEmptyException dnee) {
      System.err.printf("%s: not empty%n", target);
    } catch (IOException ioe) {
      System.err.printf("I/O error: %s%n", ioe.getMessage());
    }
  }
}
