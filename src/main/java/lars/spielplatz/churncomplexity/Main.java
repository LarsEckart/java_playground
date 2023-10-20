package lars.spielplatz.churncomplexity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Main {

  public static void main(String[] args) throws IOException {
    Path workingDir = Path.of("src/main/resources");
    Map<String, Integer> complexity = parseComplexity(workingDir);

    Map<String, Integer> churn = parseChurn(workingDir);

    Map<String, Data> result = combine(complexity, churn);

    // result.forEach((key, value) -> System.out.println(String.format("[%s, %s],", value.churn(),
    // value.complexity())));

    result.forEach(
        (key, value) -> {
          if (value.churn() > 20) {
            System.out.println(value);
          }
        });
  }

  private static Map<String, Data> combine(
      Map<String, Integer> complexity, Map<String, Integer> churn) {
    Map<String, Data> result = new HashMap<>();

    Set<String> allKeys = new HashSet<>();
    allKeys.addAll(complexity.keySet());
    allKeys.addAll(churn.keySet());
    for (String key : allKeys) {
      if (key.contains("SoneraUser")) {
        continue;
      }
      int churnValue = churn.getOrDefault(key, -1);
      if (churnValue == -1) {
        continue;
      }
      int complexityValue = complexity.getOrDefault(key, -1);
      if (complexityValue == -1) {
        continue;
      }
      Data newObject = new Data(key, churnValue, complexityValue);
      result.put(key, newObject);
    }
    return result;
  }

  private static Map<String, Integer> parseComplexity(Path workingDir) throws IOException {
    Path file = workingDir.resolve("complexity.txt");
    String sonarList = Files.readString(file);
    SonarParser sonarParser = new SonarParser();
    return sonarParser.complexity(sonarList);
  }

  private static Map<String, Integer> parseChurn(Path workingDir) throws IOException {
    Path gitResult = workingDir.resolve("churn.txt");
    String gitStats = Files.readString(gitResult);
    GitParser gitParser = new GitParser();
    return gitParser.churn(gitStats);
  }

  private static void executeProcessAndRead() {
    ProcessBuilder processBuilder = new ProcessBuilder();

    processBuilder.command(
        "bash",
        "-c",
        "git log --format=format: --name-only --since=12.month | grep '\\.java$' \\\n"
            + "| sort \\\n"
            + "| uniq -c > test.txt");
    try {
      Process process = processBuilder.start();

      StringBuilder output = new StringBuilder();

      try (InputStreamReader in =
          new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)) {
        try (BufferedReader reader = new BufferedReader(in)) {

          String line;
          while ((line = reader.readLine()) != null) {
            output.append(line + "\n");
          }
        }
      }

      int exitVal = process.waitFor();
      if (exitVal == 0) {
        System.out.println("Success!");
        System.out.println(output);
        System.exit(0);
      } else {
        // abnormal...
      }

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
