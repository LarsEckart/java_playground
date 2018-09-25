package lars.java11;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
}
