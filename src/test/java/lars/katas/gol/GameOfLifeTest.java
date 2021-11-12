package lars.katas.gol;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class GameOfLifeTest {

  @Test
  void cellWithNoNeighborsDies() {
    Cell cell = new Cell();
    cell.next(Collections.emptyList());
    assertEquals(0, cell.state());
  }

  @Test
  void newCellIsAlive() {
    Cell cell = new Cell();
    assertEquals(1, cell.state());
  }

  @Test
  void cellWith2NeighborsStaysAlive() {
    Cell cell = new Cell();
    cell.next(List.of(new Cell(), new Cell()));
    assertEquals(1, cell.state());
  }

  @Test
  void liveCellWith3NeighborsStaysAlive() {
    Cell cell = new Cell();
    cell.next(List.of(new Cell(), new Cell(), new Cell()));
    assertEquals(1, cell.state());
  }

  @Test
  void cellWith4NeighborsDies() {
    Cell cell = new Cell();
    cell.next(List.of(new Cell(), new Cell(), new Cell(), new Cell()));
    assertEquals(0, cell.state());
  }

  @Test
  void deadCellComesAliveWith3Neighbors() {
    Cell cell = Cell.dead();
    cell.next(List.of(new Cell(), new Cell(), new Cell()));
    assertEquals(1, cell.state());
  }

  @Test
  void newGridPrintsAllDeadCells() {
    Grid grid = new Grid(2, 2);
    String result = grid.print();
    assertEquals("..\n..\n", result);
  }

  @Test
  void newGridPrintsAlsoAliveCells() {
    Grid grid = new Grid(2, 2);
    grid.placeAlive(0,0);
    String result = grid.print();
    assertEquals("*.\n..\n", result);
  }
}
