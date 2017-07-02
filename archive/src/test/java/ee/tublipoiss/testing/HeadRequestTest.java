package ee.tublipoiss.testing;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;

public class HeadRequestTest {

    @Test
    public void should_not_throw() throws Exception {
        // given
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .head()
                .url(mockWebServer.url("/"))
                .build();

        final String responseBody = "{\"hello\":\"world\"}";
        final int length = responseBody.getBytes().length;
        final MockResponse mockResponseWithBody = new MockResponse().clearHeaders().setResponseCode(200)
                                                                       .setHeader("Content-Length", length)
                                                                       .setHeader("Content-Type", "application/json")
                                                                       .setBody(responseBody);
        mockWebServer.enqueue(mockResponseWithBody);

        final Response response = okHttpClient.newCall(request).execute();
        System.out.println(response.header("content-length"));
        System.out.println(response.body().string());
        // when

        // then

    }
}
