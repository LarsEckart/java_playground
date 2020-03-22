package lars.spielplatz.java11;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

public class AstroClient {

  private HttpClient client;
  private Gson gson;

  public AstroClient(HttpClient client, Gson gson) {
    this.client = client;
    this.gson = gson;
  }

  public AstroClient() {
    client =
        HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(2))
            .build();
    gson = new Gson();
  }

  public AstroResponse getSync(String url) throws IOException, InterruptedException {
    HttpResponse<String> response = client.send(request(url), HttpResponse.BodyHandlers.ofString());
    return getResponse(response.body());
  }

  public CompletableFuture<AstroResponse> getAsync(String url) {
    return client
        .sendAsync(request(url), HttpResponse.BodyHandlers.ofString())
        .thenApply(HttpResponse::body)
        .thenApply(this::getResponse);
  }

  private HttpRequest request(String url) {
    return HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
  }

  private AstroResponse getResponse(String json) {
    return gson.fromJson(json, AstroResponse.class);
  }
}
