package ee.lars.tictactoe;

public class TicTacToe {

  private static final char EMPTY_FIELD_INDICATOR = 'e';

  private Grid grid;
  private boolean isPlayerX = true;

  public TicTacToe() {
    this.grid = new Grid(3, EMPTY_FIELD_INDICATOR);
  }

  public void placePiece(int row, int column) {
    verifyPiecePlacement(row, "row");
    verifyPiecePlacement(column, "column");
    placePieceOnField(row, column);
  }

  private void verifyPiecePlacement(int place, String type) {
    if (grid.validPlacement(place)) {
      throw new IllegalArgumentException("not a valid " + type);
    }
  }

  private void placePieceOnField(int row, int column) {
    if (!this.grid.fieldEmpty(row, column)) {
      throw new IllegalArgumentException("space already occupied");
    }
    this.grid.markField(row, column, isPlayerX ? 'X' : 'O');
    isPlayerX = !isPlayerX;
  }

  public char getPlayerToken(int row, int column) {
    return this.grid.getFieldMarking(row, column);
  }

  public String getWinner() {

    if (hasHorizontalRow('X')) {
      return "X wins";
    }
    if (hasHorizontalRow('O')) {
      return "O wins";
    }

    return "no winner";
  }

  private boolean hasHorizontalRow(char playerSign) {
    for (int row = 1; row < 4; row++) {
      if (grid.getFieldMarking(row, 1) == playerSign
          && grid.getFieldMarking(row, 2) == playerSign
          && grid.getFieldMarking(row, 3) == playerSign) {
        return true;
      }
    }
    return false;
  }
}
