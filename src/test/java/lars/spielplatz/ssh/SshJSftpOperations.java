package lars.spielplatz.ssh;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

public class SshJSftpOperations implements SftpOperations {

  private final SSHClient sshClient;
  private final SFTPClient sftpClient;
  private Path path;

  private SshJSftpOperations(String user, String password, String server, Integer port) {
    try {
      SSHClient client = new SSHClient();
      client.addHostKeyVerifier(new PromiscuousVerifier());
      client.connect(server, port);
      client.authPassword(user, password);
      sshClient = client;
      sftpClient = sshClient.newSFTPClient();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static SshJSftpOperations with(String user, String password, String localhost,
      Integer port) {
    return new SshJSftpOperations(user, password, localhost, port);
  }

  public void changeDirectory(Path path) {
    this.path = path;
  }

  public void copyTo(Path path) {
    try {
      String source = path.toString();
      Path p = Paths.get(this.path.toString(), path.toFile().getName());
      sftpClient.put(source, p.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public List<String> listFiles(Path path) {
    try {
      return sftpClient.ls(path.toString()).stream().map(RemoteResourceInfo::getName).toList();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void close() {
    try {
      if (sftpClient != null) {
        sftpClient.close();
      }
      if (sshClient != null) {
        sshClient.disconnect();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
