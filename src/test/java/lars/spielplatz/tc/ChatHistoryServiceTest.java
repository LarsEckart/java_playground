package lars.spielplatz.tc;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

@Testcontainers
class ChatHistoryServiceTest {

  static final DockerImageName localstackImage =
      DockerImageName.parse("localstack/localstack:3.5.0");

  @Container
  public static LocalStackContainer localstack =
      new LocalStackContainer(localstackImage).withServices(S3);

  public S3Client s3;

  @BeforeEach
  void beforeAll() {
    s3 =
        S3Client.builder()
            .endpointOverride(localstack.getEndpoint())
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(
                        localstack.getAccessKey(), localstack.getSecretKey())))
            .region(Region.of(localstack.getRegion()))
            .build();

    s3.createBucket(CreateBucketRequest.builder().bucket("my-bucket").build());
  }

  @Test
  void store_and_retrieve() {
    var chatHistory = new ChatHistoryService(s3);
    chatHistory.whoSaidWhatWhen("Lars", "hello friends", LocalDateTime.now().minusMinutes(5));
    chatHistory.whoSaidWhatWhen("Lars", "how are you", LocalDateTime.now().minusMinutes(4));
    chatHistory.whoSaidWhatWhen("Friends", "I'm fine!", LocalDateTime.now().minusMinutes(4));
    chatHistory.whoSaidWhatWhen("Lars", "bye bye", LocalDateTime.now().minusMinutes(2));

    List<String> lars = chatHistory.history("Lars");
    assertThat(lars).hasSize(3);
    assertThat(lars)
        .contains("[2024-09-26T20:02:45.838660] Lars: hello friends", "how are you", "bye bye");
  }

  @Disabled("This test is currently failing on purpose")
  @Test
  void store_and_retrieve_has_a_bug() {
    ChatHistoryService chatHistoryService = new ChatHistoryService(s3);
    chatHistoryService.whoSaidWhatWhen("Bob", "hello Alice", LocalDateTime.now().minusMinutes(5));
    chatHistoryService.whoSaidWhatWhen(
        "Alice", "hey Bob! how are you", LocalDateTime.now().minusMinutes(4));

    assertThat(chatHistoryService.history("Bob")).hasSize(1);
  }
}
