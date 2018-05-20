package ee.lars.tictactoe;

public class TicTacToe {

  private static final char EMPTY_FIELD_INDICATOR = 'e';

  private char[][] placedPieces = new char[3][3];
  private boolean isPlayerX = true;

  public TicTacToe() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        placedPieces[i][j] = EMPTY_FIELD_INDICATOR;
      }
    }
  }

  public void placePiece(int column, int row) {
    verifyPiecePlacedWithinAllowedColumns(column);
    verifyPiecePlacedWithinAllowedRows(row);
    placePieceOnField(column, row);
  }

  private void verifyPiecePlacedWithinAllowedColumns(int column) {
    if (column > 3 || column < 1) {
      throw new RuntimeException("x axis value too large");
    }
  }

  private void verifyPiecePlacedWithinAllowedRows(int row) {
    if (row > 3 || row < 1) {
      throw new RuntimeException("y axis value too large");
    }
  }

  private void placePieceOnField(int column, int row) {
    if (placedPieces[column - 1][row - 1] != EMPTY_FIELD_INDICATOR) {
      throw new RuntimeException("space already occupied");
    }
    placedPieces[column - 1][row - 1] = isPlayerX ? 'X' : 'O';
    isPlayerX = !isPlayerX;
  }

  public char getPlayerToken(int column, int row) {
    return placedPieces[column - 1][row - 1];
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
    for (int i = 0; i < 3; i++) {
      if (placedPieces[i][0] == playerSign && placedPieces[i][1] == playerSign && placedPieces[i][2] == playerSign) {
        return true;
      }
    }
    return false;
  }

  /* for debug purpose because multidimensional arrays are tough */
  private void printGrid() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        System.out.printf("%s ", placedPieces[i][j]);
      }
      System.out.println();
    }
  }
}
