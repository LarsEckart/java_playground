package lars.katas.gameoflife;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static lars.katas.gameoflife.CellBuilder.aCell;
import static org.assertj.core.api.Assertions.assertThat;

class GameOfLife {

    @Test
    void new_world_has_8_rows() {
        // given

        // when
        World world = World.createNew();

        // then
        assertThat(world.rows()).isEqualTo(8);
    }

    @Test
    void new_world_has_8_columns() {
        // given

        // when
        World world = World.createNew();

        // then
        assertThat(world.columns()).isEqualTo(8);
    }

    @ParameterizedTest
    @MethodSource("allCells")
    void new_world_has_no_living_cells(Cell cell) {
        // given

        // when
        World world = World.createNew();

        // then
        assertThat(world.isAliveAt(cell)).isFalse();
    }

    private static Stream<Arguments> allCells() {
        List<Cell> cells = new ArrayList<>(64);
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                cells.add(aCell().atColumn(i).atRow(j));
            }
        }

        return cells.stream().map(Arguments::of);
    }

    @Test
    void living_cell_can_be_added_to_world() {
        // given
        World world = World.createNew();
        Cell livingCell = aCell().atColumn(1).atRow(1);

        // when
        world.lifeAt(livingCell);

        // then
        assertThat(world.isAliveAt(livingCell)).isTrue();
    }

    @Disabled("on hold")
    @Test
    void evolution_step_single_alive_cell_will_die() {
        // given
        World world = World.createNew();
        Cell livingCell = aCell().atColumn(1).atRow(1);
        world.lifeAt(livingCell);

        // when
        world.evolve();

        // then
        assertThat(world.isAliveAt(livingCell)).isFalse();
    }
}
