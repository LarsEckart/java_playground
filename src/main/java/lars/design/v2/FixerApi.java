package lars.design.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class FixerApi {

  public ExchangeRate exchangeRateFor(Currency from, Currency to)
      throws IOException, InterruptedException {
    HttpClient httpClient = HttpClient.newHttpClient();
    String accessKey = System.getenv("fixer_api_access_key");

    var url =
        "http://data.fixer.io/api/latest?access_key=%s&base=%s&symbols=%s".formatted(
            accessKey,
            from.asString(),
            to.asString());
    var request = HttpRequest.newBuilder(URI.create(url)).GET().build();
    var response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
    var json = new ObjectMapper().readTree(response.body());

    var rate = json.get("rates").get("USD").asDouble();
    return new ExchangeRate(from, to, rate);
  }

}
