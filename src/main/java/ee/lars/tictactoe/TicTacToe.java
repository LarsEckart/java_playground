package ee.lars.tictactoe;

class TicTacToe {

  private static final char EMPTY_FIELD_INDICATOR = 'e';
  private static final int DEFAULT_GRID_SIZE = 3;

  private Grid grid;
  private boolean isPlayerX = true;

  public TicTacToe() {
    this.grid = new Grid(DEFAULT_GRID_SIZE, EMPTY_FIELD_INDICATOR);
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
    if (hasHorizontalWinningCondition('X') || hasVerticalWinningCondition('X')) {
      return "X wins";
    }
    if (hasHorizontalWinningCondition('O') || hasVerticalWinningCondition('O')) {
      return "O wins";
    }

    return "no winner";
  }

  private boolean hasHorizontalWinningCondition(char playerSign) {
    return grid.anyRowCompleteFor(playerSign);
  }

  private boolean hasVerticalWinningCondition(char playerSign) {
    return grid.anyColumnCompleteFor(playerSign);
  }
}
