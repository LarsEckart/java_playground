package lars.spielplatz.tc;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

@Testcontainers
@DisabledOnOs(OS.WINDOWS)
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
    // Fixed time for testing
    var fixedInstant = Instant.parse("2024-09-26T20:07:45.838660Z");
    var fixedClock = Clock.fixed(fixedInstant, ZoneId.of("UTC"));
    var chatHistory = new ChatHistoryService(s3, fixedClock);

    var now = LocalDateTime.now(fixedClock);
    chatHistory.whoSaidWhatWhen("Lars", "hello friends", now.minusMinutes(5));
    chatHistory.whoSaidWhatWhen("Lars", "how are you", now.minusMinutes(4));
    chatHistory.whoSaidWhatWhen("Friends", "I'm fine!", now.minusMinutes(4));
    chatHistory.whoSaidWhatWhen("Lars", "bye bye", now.minusMinutes(2));

    List<String> lars = chatHistory.history("Lars");
    assertThat(lars).hasSize(3);
    assertThat(lars)
        .containsExactly(
            "[2024-09-26T20:02:45.838660] Lars: hello friends",
            "[2024-09-26T20:03:45.838660] Lars: how are you",
            "[2024-09-26T20:05:45.838660] Lars: bye bye");
  }

  @Test
  void store_and_retrieve_has_a_bug() {
    var fixedInstant = Instant.parse("2024-09-26T20:07:45.838660Z");
    var fixedClock = Clock.fixed(fixedInstant, ZoneId.of("UTC"));
    ChatHistoryService chatHistoryService = new ChatHistoryService(s3, fixedClock);

    var now = LocalDateTime.now(fixedClock);
    chatHistoryService.whoSaidWhatWhen("Bob", "hello Alice", now.minusMinutes(5));
    chatHistoryService.whoSaidWhatWhen("Alice", "hey Bob! how are you", now.minusMinutes(4));

    assertThat(chatHistoryService.history("Bob")).hasSize(1);
  }
}
