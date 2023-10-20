package lars.spielplatz.java11;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

/**
 * from
 * https://github.com/ralscha/blog2019/blob/master/java11httpclient/client/src/main/java/ch/rasc/httpclient/WebSocketDemo.java
 *
 * <p>Notice that the Java 11 WebSocket client, like the WebSocket API in the browser, does not
 * automatically reconnect to a server when the connection breaks. Therefore, if your application
 * requires a permanent connection, you need to build a reconnection mechanism on top of the
 * WebSocket client. For example, you could start a new WebSocket connection from the onClose event
 * or implement a more robust mechanism by sending heartbeat messages. If they don't arrive, tear
 * down the connection and rebuild it.
 */
public class WebSocketDemo {

  public static void main(String[] args) throws InterruptedException {

    Listener wsListener =
        new Listener() {
          @Override
          public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {

            System.out.println("onText: " + data);

            return Listener.super.onText(webSocket, data, last);
          }

          @Override
          public void onOpen(WebSocket webSocket) {
            System.out.println("onOpen");
            Listener.super.onOpen(webSocket);
          }

          @Override
          public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
            System.out.println("onClose: " + statusCode + " " + reason);
            return Listener.super.onClose(webSocket, statusCode, reason);
          }
        };

    var client = HttpClient.newHttpClient();

    WebSocket webSocket =
        client
            .newWebSocketBuilder()
            .buildAsync(URI.create("wss://localhost:8443/wsEndpoint"), wsListener)
            .join();
    webSocket.sendText("hello from the client", true);

    TimeUnit.SECONDS.sleep(30);
    webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "ok");
  }
}
