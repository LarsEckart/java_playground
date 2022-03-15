package lars.scripts;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FindGradleRepoAndReportVersion {

  private static final Logger log = getLogger(FindGradleRepoAndReportVersion.class);

  public static void main(String[] args) throws IOException, InterruptedException {
    final List<Path> gradleProjects = findAllGradleProjects();

    for (Path gradleProject : gradleProjects) {
      final List<String> complicatedOrAndroidGradleProject =
          List.of(
              "/Users/lars/GitHub/fairy-fingers",
              "/Users/lars/GitHub/fortune-cookie-shop",
              "/Users/lars/GitHub/qualitymatters",
              "/Users/lars/GitHub/componentizationArch");
      if (complicatedOrAndroidGradleProject.contains(gradleProject.toString())) {
        log.info("android app got through: " + gradleProject);
        continue;
      }
      final String version = determineGradleWrapperVersion(gradleProject);
      System.out.println(gradleProject + " : " + version);

      // TODO: but what if behind origin/master?


      try {
        Process updateGradleWrapperProcess =
            Runtime.getRuntime().exec("gradle wrapper", null, gradleProject.toFile());
        updateGradleWrapperProcess.waitFor();
        if (updateGradleWrapperProcess.exitValue() != 0) {
          System.err.println("%s need manual tweaking".formatted(gradleProject));
          continue;
        }
        Process verifyGradleProjectBuilds =
            Runtime.getRuntime().exec("./gradlew test", null, gradleProject.toFile());
        verifyGradleProjectBuilds.waitFor();
        if (verifyGradleProjectBuilds.exitValue() != 0) {
          System.err.println("Gradle build in %s needs manual tweaking".formatted(gradleProject));
          continue;
        }
      } catch (Exception e) {
        System.out.println("something went wrong");
      }
      Process gitCommitCommand =
          Runtime.getRuntime()
              .exec(
                  new String[] {"git", "commit", "-a", "-m", "'update gradle wrapper'"},
                  null,
                  gradleProject.toFile());
      gitCommitCommand.waitFor();

      System.out.println("done at %s".formatted(gradleProject));
    }
  }

  private static String determineGradleWrapperVersion(Path gradleProject) throws IOException {
    final Path path = gradleProject.resolve("gradle/wrapper/gradle-wrapper.properties");
    final String version =
        Files.lines(path)
            .filter(l -> l.startsWith("distributionUrl"))
            .map(l -> l.substring(66))
            .map(s -> s.replace("-all.zip", ""))
            .map(s -> s.replace("-bin.zip", ""))
            .findFirst()
            .orElse("boom");
    return version;
  }

  private static List<Path> findAllGradleProjects() {
    final List<Path> gradleProjects = new ArrayList<>();
    DirectoryStream.Filter<Path> filter =
        file -> {
          final boolean isDirectory = Files.isDirectory(file);
          boolean hasGradleSubFolder = Files.exists(file.resolve(Path.of("gradle")));
          boolean hasGradleBuildFile = Files.exists(file.resolve(Path.of("build.gradle")));
          boolean isProbablyAndroidApp = Files.exists(file.resolve(Path.of("app")));
          return isDirectory && hasGradleSubFolder && hasGradleBuildFile && !isProbablyAndroidApp;
        };

    try (var stream = Files.newDirectoryStream(Path.of("/Users/lars/GitHub/"), filter)) {
      for (Path path : stream) {
        gradleProjects.add(path);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return gradleProjects.stream().sorted().toList();
  }
}
