package lars.scripts;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FindGradleRepoAndReportVersion {

  public static void main(String[] args) throws Exception {
    var gradleProjects = findAllGradleProjects();
    System.out.println("found " + gradleProjects.size() + " gradle projects");

    for (Project gradleProject : gradleProjects) {

      final String version = determineGradleWrapperVersion(gradleProject);
      System.out.println(gradleProject + " : " + version);
      if (version.equals("8.5")) {
        System.out.println("already on latest version");
        continue;
      }

      // TODO: but what if behind origin/master?

      try {
        Process process = new ProcessBuilder("git", "pull").start();
        process.waitFor();
        Process updateGradleWrapperProcess =
            Runtime.getRuntime().exec(new String[]{"gradle", "wrapper"}, null, gradleProject.path().toFile());
        updateGradleWrapperProcess.waitFor();
        if (updateGradleWrapperProcess.exitValue() != 0) {
          System.out.printf("Gradle wrapper in %s needs manual tweaking%n", gradleProject);
          continue;
        }
        Process verifyGradleProjectBuilds =
            Runtime.getRuntime().exec(new String[]{"./gradlew", "test"}, null, gradleProject.path().toFile());
        verifyGradleProjectBuilds.waitFor();
        if (verifyGradleProjectBuilds.exitValue() != 0) {
          System.out.printf("Gradle project %s does not build%n", gradleProject);
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
                  gradleProject.path().toFile());
      gitCommitCommand.waitFor();

      System.out.printf("done at %s\n", gradleProject);
    }
  }

  private static String determineGradleWrapperVersion(Project gradleProject) throws IOException {
    final Path path = gradleProject.path().resolve("gradle/wrapper/gradle-wrapper.properties");
    try (Stream<String> lines = Files.lines(path)) {
      return lines
          .filter(l -> l.startsWith("distributionUrl"))
          .map(l -> l.substring(66))
          .map(s -> s.replace("-all.zip", ""))
          .map(s -> s.replace("-bin.zip", ""))
          .findFirst()
          .orElse("boom");
    }
  }

  private static List<Project> findAllGradleProjects() throws IOException {
    final List<Project> gradleProjects = new ArrayList<>();

    Predicate<Path> isDirectory = Files::isDirectory;
    Predicate<Path> hasGradleSubFolder = p -> Files.exists(p.resolve(Path.of("gradle")));
    Predicate<Path> hasGradleBuildFile = p -> Files.exists(p.resolve(Path.of("build.gradle")));
    Predicate<Path> isProbablyAndroid = p -> Files.exists(p.resolve(Path.of("app")));
    DirectoryStream.Filter<Path> filter =
        file ->
            isDirectory
                .and(hasGradleSubFolder)
                .and(hasGradleBuildFile)
                .and(Predicate.not(isProbablyAndroid))
                .test(file);

    try (var stream = Files.newDirectoryStream(Path.of("/Users/lars/GitHub/"), filter)) {
      for (Path path : stream) {
        gradleProjects.add(new Project(path));
      }
    }

    return gradleProjects.stream().sorted().toList();
  }

  record Project(Path path) implements Comparable<Project> {

    String name() {
      return path.getFileName().toString();
    }

    @Override
    public String toString() {
      return name();
    }

    @Override
    public int compareTo(Project o) {
      return name().compareTo(o.name());
    }
  }
}
