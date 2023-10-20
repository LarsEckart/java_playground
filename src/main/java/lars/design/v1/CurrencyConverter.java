package lars.design.v1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class CurrencyConverter {

  public Money convert(Money money, Currency to) throws IOException, InterruptedException {
    HttpClient httpClient = HttpClient.newHttpClient();
    String accessKey = System.getenv("fixer_api_access_key");

    String url =
        "http://data.fixer.io/api/latest?access_key=%s&base=%s&symbols=%s"
            .formatted(accessKey, money.getCurrency().asString(), to.asString());

    HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
    HttpResponse<InputStream> response =
        httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());

    JsonNode json = new ObjectMapper().readTree(response.body());

    double rate = json.get("rates").get(to.asString()).asDouble();

    return money.convert(to, rate);
  }
}
