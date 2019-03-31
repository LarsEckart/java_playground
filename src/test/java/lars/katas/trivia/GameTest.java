package lars.katas.trivia;

import org.approvaltests.Approvals;
import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

class GameTest {

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class ApprovalTests {

        @Test
        void a_single_approval_test() {
            Approvals.verify(this.runGame(42));
        }

        @Test
        void multiple_inputs_for_an_approval_test() throws Exception {
            Long[] seeds = new Long[100];
            for (int i = 0; i < seeds.length; i++) {
                seeds[i] = (long) i + i + i;
            }
            CombinationApprovals.verifyAllCombinations(this::runGame, seeds);
        }

        private String runGame(long seed) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));
            GameRunner.play(new Random(seed));

            return out.toString();
        }
    }
}
