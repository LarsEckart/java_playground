package lars.scripts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GetCommandToCloneAllMyRepositories {

  private static final TypeReference<List<Repository>> ref = new TypeReference<>() {};
  private static final ObjectMapper objectMapper =
      new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  public static void main(String[] args) throws IOException, InterruptedException {
    final String githubToken = System.getenv("GITHUB_TOKEN");
    final HttpClient httpClient =
        HttpClient.newBuilder()
            .version(Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(2))
            .build();

    List<Repository> result = new ArrayList<>();

    int page = 1;
    boolean isFinished;
    do {
      final HttpRequest request =
          HttpRequest.newBuilder()
              .uri(URI.create("https://api.github.com/user/repos?per_page=100&page=" + page))
              .GET()
              .header("Authorization", "Bearer " + githubToken)
              .header("Accept", "application/vnd.github.v3+json")
              .build();

      final HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

      List<Repository> repositories = objectMapper.readValue(response.body(), ref);
      isFinished = repositories.size() < 100;
      result.addAll(
          repositories.stream().filter(GetCommandToCloneAllMyRepositories::ownedByMe).toList());
      page++;
    } while (!isFinished);

    result.forEach(r -> System.out.printf("git clone %s;", r.url()));
  }

  private static boolean ownedByMe(Repository r) {
    return "LarsEckart".equals(r.owner().login());
  }
}
