package lars;

import static org.assertj.core.api.Assertions.assertThat;

import com.spun.util.ObjectUtils;
import com.spun.util.StringUtils;
import com.spun.util.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

@ExtendWith(DevTest.TestCommitRevertExtension.class)
class DevTest {

  @Test
  void name() throws Exception {
    assertThat(true).isTrue();
  }

  static class TestCommitRevertExtension implements TestWatcher {

    public static boolean PRINT_ONLY = false;

    @Override
    public void testSuccessful(ExtensionContext context) {
      try {
        File gitDir = getHeadOfGit();
        if (gitDir == null) {
          System.out.println("No .git repo found at " + new File(".").getAbsolutePath());
          return;
        }
        System.out.println("lets commit");
        commit(gitDir);
      } catch (IOException e) {
        e.printStackTrace();
      }

      System.out.println("test Successful");
    }

    private static File getHeadOfGit() throws IOException {
      File file = new File(".").getCanonicalFile();
      while (true) {
        File gitFile = new File(file, ".git");
        if (gitFile.exists()) {
          return file;
        } else {
          file = file.getParentFile();
          if (file == null) {
            return null;
          }
        }
      }
    }

    private static void commit(File gitDir) {
      if (isGitEmpty(gitDir)) {
        System.out.println("Nothing to commit");
        return;
      }
      if (PRINT_ONLY) {
        System.out.println(" would normally commit at this point");
        return;
      }
      String message = "test";//askForCommitMessage.call();
      if (!StringUtils.isEmpty(message)) {
        runOnConsole(gitDir, "git", "add", "-A");
        runOnConsole(gitDir, "git", "commit", "-m", message);
      }
    }

    private static void revertGit(File gitDir)
    {
      runOnConsole(gitDir, "git", "clean", "-fd");
      runOnConsole(gitDir, "git", "reset", "--hard", "HEAD");
      String helpMessage = "Test Failed, reverting...\n\n Remember to auto refresh \n preferences > general>  workspace > ✓ refresh using native Hooks";
      System.out.println(helpMessage);
    }

    private static boolean isGitEmpty(File gitDir) {
      runOnConsole(gitDir, new String[] {"git", "status"});
      try {
        Process p = Runtime.getRuntime().exec(new String[] {"git", "status"}, null, gitDir);
        p.waitFor();
        String result = FileUtils.readStream(p.getInputStream());
        return result.contains("nothing to commit");
      } catch (Exception e) {
        ObjectUtils.throwAsError(e);
      }
      return false;
    }

    private static void runOnConsole(File workingDir, String... cmdArgs) throws Error {
      if (PRINT_ONLY) {
        System.out.println(Arrays.toString(cmdArgs));
        return;
      }
      try {
        Process p = Runtime.getRuntime().exec(cmdArgs, null, workingDir);
        p.waitFor();
        System.out.println(com.spun.util.io.FileUtils.readStream(p.getInputStream()));
        System.out.println(com.spun.util.io.FileUtils.readStream(p.getErrorStream()));
      } catch (Exception e) {
        ObjectUtils.throwAsError(e);
      }
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
      try {
        File gitDir = getHeadOfGit();
        if (gitDir == null) {
          System.out.println("No .git repo found at " + new File(".").getAbsolutePath());
          return;
        }
        System.out.println("lets commit");
        revertGit(gitDir);
      } catch (IOException e) {
        e.printStackTrace();
      }

      System.out.println("test failed");
    }
  }
}
