package lars.spielplatz.ssh;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class JSchSftpOperations implements SftpOperations {

  private final Session session;
  private final ChannelSftp channel;

  private JSchSftpOperations(String user, String password, String server, Integer port) {
    try {
      JSch jsch = new JSch();
      session = jsch.getSession(user, server, port);
      session.setPassword(password);
      java.util.Properties config = new java.util.Properties();
      config.put("StrictHostKeyChecking", "no");
      session.setConfig(config);
      session.connect();
      channel = (ChannelSftp) session.openChannel("sftp");
      channel.connect();
    } catch (JSchException e) {
      throw new RuntimeException(e);
    }
  }

  public static JSchSftpOperations with(String user, String password, String localhost, Integer port) {
    return new JSchSftpOperations(user, password, localhost, port);
  }

  @Override
  public void close() {
    if (channel != null) {
      channel.disconnect();
    }
    if (session != null) {
      session.disconnect();
    }
  }

  @Override
  public void changeDirectory(Path path) {
    try {
      this.channel.cd(path.toString());
    } catch (SftpException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void copyTo(Path path) {
    File file = path.toFile();
    try (FileInputStream input = new FileInputStream(file)) {
      try {
        channel.put(input, file.getName());
      } catch (SftpException e) {
        throw new RuntimeException(e);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<String> listFiles(Path path) {
    try {
      return channel.ls(path.toString()).stream()
          .map(LsEntry::getFilename)
          .filter(filename -> !(".".equals(filename) || "..".equals(filename)))
          .toList();
    } catch (SftpException e) {
      throw new RuntimeException(e);
    }
  }
}
