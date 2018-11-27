package lars.cracking.elevator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ElevatorProblemTest {

    @Test
    void should_return_6_when_example_data() throws Exception {
        // given
        int[] A = {40, 40, 100, 80, 20};
        int[] B = {3, 3, 2, 2, 3};
        int M = 3;
        int X = 5;
        int Y = 200;

        // when
        final long totalStops = new ElevatorProblem().solve(A, B, M, X, Y);

        // then
        assertThat(totalStops).isEqualTo(6);
    }
}
