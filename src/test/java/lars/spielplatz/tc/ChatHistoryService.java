package lars.spielplatz.tc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

class ChatHistoryService {

  private static final String bucketName = "my-bucket";

  private S3Client s3Client;

  public ChatHistoryService(S3Client s3Client) {
    this.s3Client = s3Client;
  }

  void whoSaidWhatWhen(String who, String what, LocalDateTime when) {
    try {
      // [2024-8-24 17:34] Lars: hello world
      var line = new ChatHistoryLine(who, what, when).renderChatHistoryLine();
      var getObjectRequest =
          GetObjectRequest.builder().bucket(bucketName).key("chat_history").build();
      String previousVersion;
      try {
        var response = s3Client.getObject(getObjectRequest);
        previousVersion = new String(response.readAllBytes());
      } catch (NoSuchKeyException e) {
        previousVersion = "";
      }

      Path tempFile = Files.createTempFile(null, null);
      String newContent = previousVersion + line + "\n";
      var path = Files.writeString(tempFile, newContent);
      var putObjectRequest =
          PutObjectRequest.builder().bucket(bucketName).key("chat_history").build();
      s3Client.putObject(putObjectRequest, path);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  List<String> history(String who) {
    try {
      var getObjectRequest =
          GetObjectRequest.builder().bucket(bucketName).key("chat_history").build();
      var response = s3Client.getObject(getObjectRequest);
      var allContent = new String(response.readAllBytes());
      return allContent.lines().filter(getStringPredicate(who)).toList();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static @NotNull Predicate<String> getStringPredicate(String who) {
    return line -> line.contains(who);
  }

  // check with a regex that name comes after the timestamp and before the colon
  private static @NotNull Predicate<String> getCorrectStringPredicate(String who) {
    return line -> line.matches(".*\\[.*\\] " + who + ":.*");
  }
}
