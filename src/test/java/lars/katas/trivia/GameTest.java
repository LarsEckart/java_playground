package lars.katas.trivia;

import org.approvaltests.Approvals;
import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

class GameTest {

    @Test
    void one() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        System.setOut(new PrintStream(out));
        GameRunner.play(new Random(42L));

        Approvals.verify(out.toString());
    }

    @Test
    void many() throws Exception {
        Long[] seeds = new Long[100];
        for (int i = 0; i < seeds.length; i++) {
            seeds[i] = (long) i + i + i;
        }
        CombinationApprovals.verifyAllCombinations(this::runGame, seeds);
    }

    private String runGame(long seed) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        System.setOut(new PrintStream(out));
        GameRunner.play(new Random(seed));

        return out.toString();
    }
}
