package lars.spielplatz.ssh;

import static org.slf4j.LoggerFactory.getLogger;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.scrubbers.DateScrubber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DisabledOnOs(OS.WINDOWS) // no docker on github actions windows
@Testcontainers
public class SFtpUploadWithTestContainersTest {

  private static final Logger log = getLogger(SFtpUploadWithTestContainersTest.class);

  private static final String USERNAME = "user";
  private static final String PASSWORD = "password";
  private static final String REMOTE_PATH = "upload";

  @TempDir
  private static File directory;

  @Container
  private static final GenericContainer<?> sftp = new GenericContainer<>(
      new ImageFromDockerfile()
          .withDockerfileFromBuilder(builder ->
              builder
                  .from("atmoz/sftp:latest")
                  .run("mkdir -p /home/" + USERNAME + "/upload; chmod -R 007 /home/" + USERNAME)
                  .build()))
      .withExposedPorts(22)
      .withCommand(USERNAME + ":" + PASSWORD + ":1001:::" + REMOTE_PATH);

  @Test
  void uploadOnlyCsvFiles() throws IOException {
    var folderWithFiles = createTempFilesOnLocalStorage(
        "users1.csv", "users2.csv", "users3.csv", "secrets.txt", "config", "users4.csv");

    newUploadAllCsvFiles(folderWithFiles);
  }

  private static File createTempFilesOnLocalStorage(String... fileNames) {
    var files = Arrays.asList(fileNames);
    files.forEach(fileName -> {
      File csvFile = new File(directory.getAbsolutePath() + "/" + fileName);
      try {
        csvFile.createNewFile();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    return new File(directory.getAbsolutePath());
  }

  private void legacyUploadAllCsvFiles(File directory) {
    final JSch jsch = new JSch();
    Session session = null;
    ChannelSftp channel = null;
    try {
      session = jsch.getSession(USERNAME, sftp.getHost(), sftp.getFirstMappedPort());
      session.setPassword(PASSWORD);
      java.util.Properties config = new java.util.Properties();
      config.put("StrictHostKeyChecking", "no");
      session.setConfig(config);
      session.connect();
      channel = (ChannelSftp) session.openChannel("sftp");
      channel.connect();

      String path = "/upload/";
      channel.cd(path);

      for (File file : directory.listFiles()) {
        if (file.isFile()) {
          String extension = "";
          int i = file.getName().lastIndexOf('.');
          if (i > 0) {
            extension = file.getName().substring(i + 1);
          }
          if ("csv".equals(extension)) {
            log.info("Uploading {}", file.getName());
            try (FileInputStream input = new FileInputStream(file)) {
              try {
                //write to ftp server
                channel.put(input, file.getName());
              } catch (SftpException e) {
                log.error("Failed to upload file {}", file.getName(), e);
              }
            }
          }
        }
      }

      Vector<LsEntry> files = channel.ls(path);
      String collect = files.stream().map(LsEntry::toString).sorted()
          .collect(Collectors.joining("\n"));

      Approvals.verify(collect, new Options(new DateScrubber("[A-Za-z]{3} \\d{2} \\d{2}:\\d{2}")));

    } catch (JSchException | SftpException | IOException e) {
      throw new RuntimeException(e);
    } finally {
      if (channel != null) {
        channel.disconnect();
      }
      if (session != null) {
        session.disconnect();
      }
    }
  }

  private void newUploadAllCsvFiles(File directory) {
    try (SftpOperations ssh = SftpOperations.withJsch(USERNAME, PASSWORD, sftp.getHost(),
        sftp.getFirstMappedPort())) {
      Path path = Path.of("/upload/");
      ssh.changeDirectory(path);

      try (Stream<Path> stream = Files.list(directory.toPath())) {
        stream
            .filter(file -> !Files.isDirectory(file))
            .filter(p -> "csv".equals(fileExtension(p.toFile().getName())))
            .forEach(ssh::copyTo);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      List<String> files = ssh.listFiles(path).stream().sorted().toList();
      String collect = String.join("\n", files);
      Approvals.verify(collect, new Options(new DateScrubber("[A-Za-z]{3} \\d{2} \\d{2}:\\d{2}")));
    }
  }

  public String fileExtension(String filename) {
    return Optional.ofNullable(filename)
        .filter(f -> f.contains("."))
        .map(f -> f.substring(filename.lastIndexOf(".") + 1))
        .orElse("");
  }
}

