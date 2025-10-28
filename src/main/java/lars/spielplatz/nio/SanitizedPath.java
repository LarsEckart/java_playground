package lars.spielplatz.nio;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SanitizedPath {

  private final Path path;

  public SanitizedPath(String pathStr) throws SecurityException {
    Path normalized = Paths.get(pathStr).normalize();
    if (normalized.isAbsolute()) {
      throw new SecurityException("Absolute paths are not allowed");
    }
    if (normalized.toString().contains("..")) {
      throw new SecurityException("Path traversal attempts are not allowed");
    }
    this.path = normalized;
  }

  public Path value() {
    return path;
  }
}
