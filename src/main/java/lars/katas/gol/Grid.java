package lars.katas.gol;

import java.util.HashMap;
import java.util.Map;

class Grid {

  int rows;
  int columns;
  Map<Coordinate, Cell> board = new HashMap<>();

  Grid(int rows, int columns) {
    this.rows = rows;
    this.columns = columns;
    init();
  }

  private void init() {
    for (int c = 0; c < columns; c++) {
      for (int r = 0; r < rows; r++) {
        board.put(new Coordinate(c, r), Cell.dead());
      }
    }
  }

  String print() {
    String result = "";
    for (int c = 0; c < columns; c++) {
      for (int r = 0; r < rows; r++) {
        result += board.get(new Coordinate(c, r));
      }
      result += "\n";
    }
    return result;
  }

  public void placeAlive(int c, int r) {
    board.put(new Coordinate(c, r), new Cell());
  }
}
