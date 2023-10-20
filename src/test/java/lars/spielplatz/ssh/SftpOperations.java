package lars.spielplatz.ssh;

import java.nio.file.Path;
import java.util.List;

public interface SftpOperations extends AutoCloseable {

  public static SftpOperations withJsch(
      String user, String password, String localhost, Integer port) {
    return JSchSftpOperations.with(user, password, localhost, port);
  }

  public static SftpOperations withSshJ(
      String user, String password, String localhost, Integer port) {
    return SshJSftpOperations.with(user, password, localhost, port);
  }

  void changeDirectory(Path path);

  void copyTo(Path path);

  List<String> listFiles(Path path);

  void close();
}
