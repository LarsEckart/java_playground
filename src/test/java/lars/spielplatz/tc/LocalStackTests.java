package lars.spielplatz.tc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

import org.junit.jupiter.api.BeforeEach;
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
class LocalStackTests {

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
    String text = "hello world\n rtfm";

    ObjectStorageService objectStorageService = new ObjectStorageService(s3);

    objectStorageService.store("key", text);
    String key = objectStorageService.retrieve("key");

    assertThat(key).isEqualTo(text);
  }
}
