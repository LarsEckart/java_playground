package lars;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LoadTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request =
                HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/test")).header("Client-Id", "Lars:1.2.3").build();

        int i = 0;
        while (i < 100) {
            CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture =
                    httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(i);
            try {
                httpResponseCompletableFuture.thenApply(HttpResponse::body).thenAccept(System.out::println).get(100, TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {

            }
            i++;
        }
        System.out.println("sent");
    }
}
