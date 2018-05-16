package ee.lars.tictactoe;

public class Board {

  private char[][] placedPieces = new char[3][3];
  private boolean isPlayerX = true;

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
    if (placedPieces[xAxis - 1][yAxis - 1] == 'O' || placedPieces[xAxis - 1][yAxis - 1] == 'X') {
      throw new RuntimeException("space already occupied");
    }
    placedPieces[xAxis - 1][yAxis - 1] = isPlayerX ? 'X' : 'O';
    isPlayerX = !isPlayerX;
  }

  public char getPlayerToken(int xAxis, int yAxis) {
    return placedPieces[xAxis - 1][yAxis - 1];
  }

  public String getWinner() {

    for (int i = 0; i < 3; i++) {
      if (placedPieces[i][0] == 'X' && placedPieces[i][0] == 'X' && placedPieces[i][0] == 'X') {
        return "X";
      }
    }
    
    return "no winner";
  }
}
