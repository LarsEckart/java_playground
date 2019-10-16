package lars.java11;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpClientExamples {

    private MockWebServer mockWebServer = new MockWebServer();

    private HttpClient client;

    private String mockWebServerUrl() {
        return mockWebServer.url("/").toString();
    }

    @Before
    public void setUp() throws Exception {
        mockWebServer.start();
        client = HttpClient.newHttpClient();
    }

    @Test
    public void get_requests() throws Exception {
        // given
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("hello world"));

        var request = HttpRequest.newBuilder()
                .uri(URI.create(mockWebServerUrl()))
                .build();

        // when
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // then
        assertThat(response.body()).isEqualTo("hello world");
        var recordedRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS);
        assertThat(recordedRequest.getMethod()).isEqualTo("GET");
    }

    @Test
    public void post_requests() throws Exception {
        // given
        mockWebServer.enqueue(new MockResponse().setResponseCode(201));

        var request = HttpRequest.newBuilder()
                .uri(URI.create(mockWebServerUrl()))
                .POST(HttpRequest.BodyPublishers.ofString("hello world"))
                .build();

        // when
        client.send(request, HttpResponse.BodyHandlers.ofString());

        // then
        var recordedRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS);
        assertThat(recordedRequest.getMethod()).isEqualTo("POST");
    }

    @Test
    public void header() throws Exception {
        // given
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        var request = HttpRequest.newBuilder()
                .uri(URI.create(mockWebServerUrl()))
                .header("x-lars", "tubli")
                .build();

        // when
        client.send(request, HttpResponse.BodyHandlers.ofString());

        // then
        var recordedRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS);
        assertThat(recordedRequest.getHeader("x-lars")).isEqualTo("tubli");
    }

    @Test
    public void headers() throws Exception {
        // given
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        String[] headers = {"hello", "world", "x-lars", "tubli"};
        var request = HttpRequest.newBuilder()
                .uri(URI.create(mockWebServerUrl()))
                .headers(headers)
                .build();

        // when
        client.send(request, HttpResponse.BodyHandlers.ofString());

        // then
        var recordedRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS);
        assertThat(recordedRequest.getHeader("hello")).isEqualTo("world");
        assertThat(recordedRequest.getHeader("x-lars")).isEqualTo("tubli");
    }

    @Test
    public void response_headers() throws Exception {
        // given
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).addHeader("x-lars", "tubli"));

        var request = HttpRequest.newBuilder()
                .uri(URI.create(mockWebServerUrl()))
                .build();

        // when
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // then
        assertThat(response.headers().allValues("x-lars")).containsExactly("tubli");
    }

    @Test
    public void out_of_the_box_headers() throws Exception {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        var request = HttpRequest.newBuilder()
                .uri(URI.create(mockWebServerUrl()))
                .build();

        // when
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // then
        var recordedRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS);
        assertThat(recordedRequest.getHeader("Connection")).isEqualTo("Upgrade, HTTP2-Settings");
        assertThat(recordedRequest.getHeader("Content-Length")).isEqualTo("0");
        assertThat(recordedRequest.getHeader("Upgrade")).isEqualTo("h2c");
        assertThat(recordedRequest.getHeader("User-Agent")).containsPattern("Java-http-client/1");
        assertThat(recordedRequest.getHeader("Host")).contains("localhost:");
    }

    @Test
    public void async_requests() throws Exception {
        // given
        client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://www.google.com"))
                .build();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // when
        CompletableFuture<HttpResponse<String>> resFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        // then
        resFuture.thenAcceptAsync(response -> {
            String body = response.body();
            System.out.println("body : " + body.length() + "[" + Thread.currentThread().getName() + "]");
        }, executorService)
                .thenRun(() -> System.out.println("Done"))
                .join(); // just to wait that main thread doesnt die and kill daemon threads with it

        executorService.shutdown();
    }
}
