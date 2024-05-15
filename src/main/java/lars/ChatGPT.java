package lars;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class ChatGPT {

  public static void main(String[] args) throws IOException, InterruptedException {
    var apiKey = System.getenv("OPENAI_API_KEY");
    var body =
        """
            {
              "model": "gpt-4o",
              "messages": [
                {
                  "role": "system",
                  "content": "You are a helpful assistant."
                },
                {
                  "role": "user",
                  "content": "What is the capital of the United States?"
                }
              ]
            }""";

    var httpRequest =
        HttpRequest.newBuilder()
            .uri(URI.create("https://api.openai.com/v1/chat/completions"))
            .header("Authorization", "Bearer " + apiKey)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

    HttpResponse<String> response;
    try (var client = HttpClient.newHttpClient()) {
      response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    String content = extractResponseContent(response);

    System.out.println(content);
  }

  private static String extractResponseContent(HttpResponse<String> response) {
    String responseBody = response.body();

    int contentIndex = responseBody.indexOf("\"content\"");
    int startOfContent = responseBody.indexOf(":", contentIndex) + 2;
    int endOfContent = responseBody.indexOf("\"logprobs\"", startOfContent) - 14;

    String content = responseBody.substring(startOfContent, endOfContent);
    return content;
  }
}
