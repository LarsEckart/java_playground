package ee.lars.tictactoe;

public class Board {

  private static final char EMPTY_FIELD_INDICATOR = 'e';

  private char[][] placedPieces = new char[3][3];
  private boolean isPlayerX = true;

  public Board() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        placedPieces[i][j] = EMPTY_FIELD_INDICATOR;
      }
    }
  }

  public void placePiece(int xAxis, int yAxis) {
    verifyXAxis(xAxis);
    verifyYAxis(yAxis);
    placePieceOnField(xAxis, yAxis);
  }

  private void verifyXAxis(int xAxis) {
    if (xAxis > 3 || xAxis < 1) {
      throw new RuntimeException("x axis value too large");
    }
  }

  private void verifyYAxis(int yAxis) {
    if (yAxis > 3 || yAxis < 1) {
      throw new RuntimeException("y axis value too large");
    }
  }

  private void placePieceOnField(int xAxis, int yAxis) {
    if (placedPieces[xAxis - 1][yAxis - 1] != EMPTY_FIELD_INDICATOR) {
      throw new RuntimeException("space already occupied");
    }
    placedPieces[xAxis - 1][yAxis - 1] = isPlayerX ? 'X' : 'O';
    isPlayerX = !isPlayerX;
  }

  public char getPlayerToken(int xAxis, int yAxis) {
    return placedPieces[xAxis - 1][yAxis - 1];
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
