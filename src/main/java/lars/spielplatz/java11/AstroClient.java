package lars.spielplatz.java11;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class AstroClient {

  private HttpClient client;

  public AstroClient(HttpClient client) {
    this.client = client;
  }

  public AstroClient() {
    client =
        HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(2))
            .build();
  }

  public AstroResponse getSync(String url) throws IOException, InterruptedException {
    HttpResponse<AstroResponse> response =
        client.send(request(url), JsonBodyHandler.jsonBodyHandler(AstroResponse.class));
    return response.body();
  }

  public CompletableFuture<AstroResponse> getAsync(String url) {
    return client
        .sendAsync(request(url), JsonBodyHandler.jsonBodyHandler(AstroResponse.class))
        .thenApply(HttpResponse::body);
  }

  private HttpRequest request(String url) {
    return HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
  }
}
