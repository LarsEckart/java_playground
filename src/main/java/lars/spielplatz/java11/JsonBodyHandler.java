package lars.spielplatz.java11;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodySubscribers;

public class JsonBodyHandler<T> implements HttpResponse.BodyHandler<T> {

  ObjectMapper objectMapper;
  private final Class<T> type;

  public static <T> JsonBodyHandler<T> jsonBodyHandler(Class<T> type) {
    return jsonBodyHandler(new ObjectMapper(), type);
  }

  public static <T> JsonBodyHandler<T> jsonBodyHandler(ObjectMapper objectMapper, Class<T> type) {
    return new JsonBodyHandler<>(objectMapper, type);
  }

  private JsonBodyHandler(ObjectMapper objectMapper, Class<T> type) {
    this.objectMapper = objectMapper;
    this.type = type;
  }

  @Override
  public HttpResponse.BodySubscriber<T> apply(HttpResponse.ResponseInfo responseInfo) {
    return BodySubscribers.mapping(
        BodySubscribers.ofByteArray(),
        byteArray -> {
          try {
            return this.objectMapper.readValue(new ByteArrayInputStream(byteArray), this.type);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }
}
