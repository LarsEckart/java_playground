package lars.advent;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Optional;

/**
 * Small helper for Advent of Code puzzles. Call {@link #forDate(int, int)} at the start of a
 * test. If the input file already exists it is returned unchanged. If it is missing, this helper
 * downloads it using the session cookie from {@code AOC_SESSION} (preferred) or the file {@code
 * ~/.config/aoc/session}. This keeps puzzle inputs out of version control while allowing tests to
 * fetch them on demand.
 */
public final class PuzzleInput {

  private static final Path INPUT_ROOT = Path.of("src", "test", "resources");
  private static final Duration TIMEOUT = Duration.ofSeconds(10);

  private PuzzleInput() {}

  public static Path forDate(int year, int day) throws IOException, InterruptedException {
    if (day < 1 || day > 25) {
      throw new IllegalArgumentException("Day must be 1-25, was: " + day);
    }

    Path inputPath = INPUT_ROOT.resolve("advent" + year).resolve("day" + twoDigits(day) + ".txt");
    if (Files.exists(inputPath)) {
      return inputPath;
    }

    if (isCiEnvironment()) {
      throw new IllegalStateException(
          "Advent input "
              + inputPath
              + " is missing and downloads are disabled in CI. "
              + "Provide the file in the repo or skip this test in CI.");
    }

    String session =
        loadSession().orElseThrow(() -> new IllegalStateException(sessionInstructions())).trim();

    HttpClient client =
        HttpClient.newBuilder()
            .connectTimeout(TIMEOUT)
            .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_NONE))
            .build();

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create("https://adventofcode.com/" + year + "/day/" + day + "/input"))
            .timeout(TIMEOUT)
            .header("Cookie", "session=" + session)
            .header("User-Agent", userAgent())
            .GET()
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() == 200) {
      Files.createDirectories(inputPath.getParent());
      Files.writeString(inputPath, response.body(), StandardCharsets.UTF_8);
      return inputPath;
    }

    String message =
        switch (response.statusCode()) {
          case 400, 404 -> "Input not available yet (did you unlock the day?)";
          case 401, 403 -> "Unauthorized: check AOC_SESSION cookie";
          default -> "Unexpected status " + response.statusCode();
        };
    throw new IOException(message + ": https://adventofcode.com/" + year + "/day/" + day);
  }

  private static Optional<String> loadSession() {
    String fromEnv = System.getenv("AOC_SESSION");
    if (fromEnv != null && !fromEnv.isBlank()) {
      return Optional.of(fromEnv);
    }

    return Optional.empty();
  }

  private static String twoDigits(int day) {
    return day < 10 ? "0" + day : String.valueOf(day);
  }

  private static String userAgent() {
    String userAgent = System.getenv("AOC_USER_AGENT");
    if (userAgent != null && !userAgent.isBlank()) {
      return userAgent;
    }
    throw new IllegalStateException("AOC_USER_AGENT is not set");
  }

  private static String sessionInstructions() {
    return "Set AOC_SESSION (from your Advent of Code cookie) to download puzzle inputs automatically.\n"
        + "You can find your session cookie by inspecting the cookies in your browser\n"
        + "after logging in to https://adventofcode.com/.";
  }

  private static boolean isCiEnvironment() {
    return envFlag("CI");
  }

  private static boolean envFlag(String name) {
    String value = System.getenv(name);
    return value != null && !value.isBlank() && !"false".equalsIgnoreCase(value);
  }
}
