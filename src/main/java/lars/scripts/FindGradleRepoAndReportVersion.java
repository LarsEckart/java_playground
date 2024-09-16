package lars.scripts;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FindGradleRepoAndReportVersion {

  public static void main(String[] args) throws Exception {
    var gradleProjects = findAllGradleProjects();
    System.out.println("found " + gradleProjects.size() + " gradle projects");

    ForkJoinPool customThreadPool = new ForkJoinPool(3);
    customThreadPool
        .submit(
            () ->
                gradleProjects.parallelStream()
                    .forEach(
                        gradleProject -> {
                          long time = System.nanoTime();
                          try {
                            System.out.printf("%s started\n", gradleProject);
                            applesauce(gradleProject);
                          } finally {
                            time = System.nanoTime() - time;
                            System.out.printf(
                                "%s done, time = %dms%n", gradleProject, (time / 1_000_000));
                          }
                        }))
        .get();
  }

  private static void applesauce(Project gradleProject) {
    if (!gradleProject.checkIfMainBranch()) {
      System.out.println(gradleProject + " not on main branch");
      gradleProject.stashAndSwitchToMain();
    }
    gradleProject.gitReset();
    gradleProject.gitPull();
    var version = gradleProject.determineGradleWrapperVersion();
    System.out.println(gradleProject + " : " + version);
    if (version.equals("8.10.1")) {
      System.out.println("already on latest version");
    }
    gradleProject.gradleUpdate();
    gradleProject.commitUpdatesFiles();
    gradleProject.gitPush();
  }

  public static String runOnCommandLine(String[] args, Project project) {
    try {
      Process command = Runtime.getRuntime().exec(args, null, project.path().toFile());
      command.waitFor();
      return command.getInputStream().toString();
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
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

    private String determineGradleWrapperVersion() {
      final Path path = path().resolve("gradle/wrapper/gradle-wrapper.properties");
      try (Stream<String> lines = Files.lines(path)) {
        return lines
            .filter(l -> l.startsWith("distributionUrl"))
            .map(l -> l.substring(66))
            .map(s -> s.replace("-all.zip", ""))
            .map(s -> s.replace("-bin.zip", ""))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("no version found"));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    private void gitPull() {
      runOnCommandLine(new String[] {"git", "pull"}, this);
    }

    private void gitReset() {
      runOnCommandLine(new String[] {"git", "reset", "--hard", "HEAD"}, this);
    }

    private boolean checkIfMainBranch() {
      var response = runOnCommandLine(new String[] {"git", "branch", "--show-current"}, this);
      return response.equals("main");
    }

    public void stashAndSwitchToMain() {
      runOnCommandLine(new String[] {"git", "stash"}, this);
      runOnCommandLine(new String[] {"git", "switch", "main"}, this);
    }

    private void gradleUpdate() {
      try {
        runOnCommandLine(new String[] {"gradle", "wrapper"}, this);
      } catch (RuntimeException e) {
        System.out.printf("Gradle wrapper in %s needs manual tweaking%n", this);
        throw new RuntimeException(e);
      }
    }

    public void commitUpdatesFiles() {
      runOnCommandLine(new String[] {"git", "add", "gradlew"}, this);
      runOnCommandLine(new String[] {"git", "add", "gradlew.bat"}, this);
      runOnCommandLine(
          new String[] {"git", "add", "gradle/wrapper/gradle-wrapper.properties"}, this);
      runOnCommandLine(new String[] {"git", "add", "gradle/wrapper/gradle-wrapper.jar"}, this);
      runOnCommandLine(new String[] {"git", "commit", "-m", "Update Gradle wrapper"}, this);
    }

    public void gitPush() {
      runOnCommandLine(new String[] {"git", "push"}, this);
    }

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
