package lars.design.v3;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class FixerApiJsonPointer implements ExchangeRates {

  private HttpClient httpClient = HttpClient.newHttpClient();
  private String accessKey = System.getenv("fixer_api_access_key");

  @Override
  public ExchangeRate exchangeRateFor(Currency from, Currency to) {
    try {
      var url =
          "http://data.fixer.io/api/latest?access_key=%s&base=%s&symbols=%s".formatted(
              accessKey,
              from.asString(),
              to.asString());
      var request = HttpRequest.newBuilder(URI.create(url)).GET().build();
      var response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
      var json = new ObjectMapper().readTree(response.body());

      JsonPointer pointer = JsonPointer.compile("/rates/" + to.asString());

      var rate = json.at(pointer).asDouble();
      return new ExchangeRate(from, to, rate);
    } catch (InterruptedException | IOException e) {
      throw new CouldNotRetrieveExchangeRate(e);
    }
  }

  static class CouldNotRetrieveExchangeRate extends RuntimeException {

    public CouldNotRetrieveExchangeRate(Exception e) {
      super(e);
    }
  }
}
