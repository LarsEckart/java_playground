package lars.katas.gameoflife;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class Game_should {

    @Test
    void parses_row_from_input() {
        // given
        String input = "Generation 1:\n"
                + "4 8\n"
                + "........\n"
                + "....*...\n"
                + "...**...\n"
                + "........";

        // when
        Game game = new Game(input);

        // then
        assertThat(game.rows()).isEqualTo(4);
    }

    @Test
    void parses_column_from_input() {
        // given
        String input = "Generation 1:\n"
                + "4 8\n"
                + "........\n"
                + "....*...\n"
                + "...**...\n"
                + "........";

        // when
        Game game = new Game(input);

        // then
        assertThat(game.columns()).isEqualTo(8);
    }

    @Test
    void parses_dead_cells_from_input() {
        // given
        String input = "Generation 1:\n"
                + "4 8\n"
                + ".......\n"
                + "....*...\n"
                + "...**...\n"
                + "........";

        // when
        Game game = new Game(input);

        // then
        assertThat(game.cellStateAt(1,1)).isEqualTo(CellState.DEAD);
    }

    private class Game {

        private final int rows;
        private final int columns;
        private final List<CellState> firstRowStates;

        public Game(String input) {
            List<String> collect = input.lines().collect(Collectors.toList());
            String s = collect.get(1);
            String[] s1 = s.split(" ");
            rows = Integer.parseInt(s1[0]);
            columns = Integer.parseInt(s1[1]);
            String firstRow = collect.get(2);
            firstRowStates = firstRow.chars().mapToObj(CellState::of).collect(Collectors.toList());
        }

        public int rows() {
            return rows;
        }

        public int columns() {
            return columns;
        }

        public CellState cellStateAt(int column, int row) {
            if (row == 1) {
                return firstRowStates.get(column);
            }
            throw new UnsupportedOperationException("implement me!");
        }
    }

    private enum CellState{
        DEAD;

        public static CellState of(int charpoint) {
            //42 * 46 dot
            return DEAD;
        }
    }
}
