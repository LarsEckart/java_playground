package lars.katas.untangled;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Collectors;

import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class PipelineTest {

  @Test
  void golden_master() {
    CombinationApprovals.verifyAllCombinations(
        this::executeProgram, new Boolean[] {true, false}, TestStatus.values());
  }

  private String executeProgram(boolean buildsSuccessfully, TestStatus noTests) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    System.setOut(new PrintStream(baos, true, java.nio.charset.StandardCharsets.UTF_8));

    Pipeline pipeline = new Pipeline(mock(Config.class), mock(Emailer.class));
    pipeline.run(
        Project.builder()
            .setDeploysSuccessfully(buildsSuccessfully)
            .setTestStatus(noTests)
            .build());

    String response = baos.toString();
    return response.lines().map(l -> l.substring(13)).collect(Collectors.joining("\n"));
  }
}
