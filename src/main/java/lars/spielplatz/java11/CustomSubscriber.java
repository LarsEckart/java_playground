package lars.spielplatz.java11;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Flow;

public class CustomSubscriber {

  public static void main(String[] args) {
    CustomSubscriber cs = new CustomSubscriber();
    String body = cs.getFlow("https://www.gov.uk/bank-holidays.json").join();
    System.out.println("got: " + body);
  }

  public CompletableFuture<String> get(String url) {
    HttpClient httpClient = HttpClient.newHttpClient();
    var request = HttpRequest.newBuilder().uri(URI.create(url)).build();

    return httpClient
        .sendAsync(request, responseInfo -> new StringSubscriber())
        .whenComplete((r, t) -> System.out.println("status code: " + r.statusCode()))
        .thenApply(HttpResponse::body);
  }

  public CompletableFuture<String> getFlow(String url) {
    HttpClient httpClient = HttpClient.newHttpClient();
    var request = HttpRequest.newBuilder().uri(URI.create(url)).build();

    HttpResponse.BodyHandler<String> bodySubscriber =
        HttpResponse.BodyHandlers.fromSubscriber(
            new StringFlowSubscriber(), StringFlowSubscriber::getBody);

    return httpClient
        .sendAsync(request, bodySubscriber)
        .whenComplete((r, t) -> System.out.println("status code: " + r.statusCode()))
        .thenApply(HttpResponse::body);
  }

  static class StringSubscriber implements HttpResponse.BodySubscriber<String> {

    private CompletableFuture<String> cf = new CompletableFuture<>();
    private Flow.Subscription subscription;
    private List<ByteBuffer> responseData = new ArrayList<>();

    @Override
    public CompletionStage<String> getBody() {
      return cf;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
      this.subscription = subscription;
      subscription.request(1L);
    }

    @Override
    public void onNext(List<ByteBuffer> item) {
      System.out.println("onNext with " + item);
      responseData.addAll(item);
      subscription.request(1L);
    }

    @Override
    public void onError(Throwable throwable) {
      cf.completeExceptionally(throwable);
    }

    @Override
    public void onComplete() {
      int size = responseData.stream().mapToInt(ByteBuffer::remaining).sum();
      byte[] ba = new byte[size];

      int offset = 0;
      for (ByteBuffer buffer : responseData) {
        int remaining = buffer.remaining();
        buffer.get(ba, offset, remaining);
        offset += remaining;
      }

      String s = new String(ba);
      cf.complete(s);
    }
  }

  static class StringFlowSubscriber implements Flow.Subscriber<List<ByteBuffer>> {

    private Flow.Subscription subscription;
    private List<ByteBuffer> responseData = new ArrayList<>();

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
      this.subscription = subscription;
      subscription.request(1L);
    }

    @Override
    public void onNext(List<ByteBuffer> item) {
      responseData.addAll(item);
      subscription.request(1L);
    }

    @Override
    public void onError(Throwable throwable) {
      System.out.println(throwable.getMessage());
    }

    private String body;

    public String getBody() {
      return body;
    }

    @Override
    public void onComplete() {
      int size = responseData.stream().mapToInt(ByteBuffer::remaining).sum();
      byte[] ba = new byte[size];

      int offset = 0;
      for (ByteBuffer buffer : responseData) {
        int remaining = buffer.remaining();
        buffer.get(ba, offset, remaining);
        offset += remaining;
      }

      body = new String(ba);
    }
  }
}
