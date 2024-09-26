package lars.spielplatz.tc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

class ObjectStorageService {

  private static final String bucketName = "my-bucket";

  private S3Client s3Client;

  public ObjectStorageService(S3Client s3Client) {
    this.s3Client = s3Client;
  }

  void store(String key, String value) {
    try {
      Path tempFile = Files.createTempFile(null, null);
      var path = Files.write(tempFile, value.getBytes(StandardCharsets.UTF_8));
      PutObjectRequest putObjectRequest =
          PutObjectRequest.builder().bucket(bucketName).key(key).build();
      s3Client.putObject(putObjectRequest, path);
    } catch (Exception e) {

    }
  }

  String retrieve(String key) {
    try {
      GetObjectRequest getObjectRequest =
          GetObjectRequest.builder().bucket(bucketName).key(key).build();
      var response = s3Client.getObject(getObjectRequest);
      return new String(response.readAllBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
