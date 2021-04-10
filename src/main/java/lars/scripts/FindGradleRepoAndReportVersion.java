package lars.scripts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class FindGradleRepoAndReportVersion {

  public static void main(String[] args) throws IOException, InterruptedException {
    final List<File> gradleProjects =
        Arrays.stream(new File("/Users/lars/GitHub/").listFiles(File::isDirectory))
            .filter(f -> Files.exists(Path.of(f.getAbsoluteFile() + File.separator + "gradle")))
            .sorted()
            .toList();

    for (int i = 0, gradleProjectsSize = gradleProjects.size(); i < gradleProjectsSize; i++) {
      File gradleProject = gradleProjects.get(i);
      // todo, check if android app
      final List<String> androidApps =
          List.of(
              "/Users/lars/GitHub/WeatherStation",
              "/Users/lars/GitHub/cveeviewer",
              "/Users/lars/GitHub/android-boilerplate",
              "/Users/lars/GitHub/cv_ee_viwer_2",
              "/Users/lars/GitHub/fairy-fingers",
              "/Users/lars/GitHub/fortune-cookie-shop",
              "/Users/lars/GitHub/componentizationArch");
      if (androidApps.contains(gradleProject.getAbsolutePath())) {
        continue;
      }
      final Path path =
          Path.of(gradleProject.getAbsolutePath() + "/gradle/wrapper/gradle-wrapper.properties");
      final String version =
          Files.lines(path)
              .filter(l -> l.startsWith("distributionUrl"))
              .map(l -> l.substring(66))
              .map(s -> s.replace("-all.zip", ""))
              .map(s -> s.replace("-bin.zip", ""))
              .findFirst()
              .orElse("boom");
      System.out.println(gradleProject + " : " + version);

      // TODO: but what if behind origin/master?
      if ("7.0".equals(version)) {
        continue;
      }

      try {
        Process updateGradleWrapperProcess =
            Runtime.getRuntime().exec("gradle wrapper", null, gradleProject);
        updateGradleWrapperProcess.waitFor();
        if (updateGradleWrapperProcess.exitValue() != 0) {
          System.err.println("%s need manual tweaking".formatted(gradleProject));
          break;
        }
        Process verifyGradleProjectBuilds =
            Runtime.getRuntime().exec("./gradlew test", null, gradleProject);
        verifyGradleProjectBuilds.waitFor();
        if (verifyGradleProjectBuilds.exitValue() != 0) {
          System.err.println("Gradle build in %s needs manual tweaking".formatted(gradleProject));
          break;
        }
      } catch (Exception e) {
        System.out.println("something went wrong");
      }
      Process gitCommitCommand =
          Runtime.getRuntime()
              .exec(
                  new String[] {"git", "commit", "-a", "-m", "'update gradle wrapper'"},
                  null,
                  gradleProject);
      gitCommitCommand.waitFor();

      System.out.println("done at %s".formatted(gradleProject));
      if (i == 5) {
        break;
      }
    }
  }
}
