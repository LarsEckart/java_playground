package ee.lars.tictactoe;

public class Board {

  private int[][] placedPieces = new int[3][3];

  public void placePiece(int xAxis, int yAxis) {
    if (xAxis > 3 || xAxis < 1 || yAxis > 3 || yAxis < 1) {
      throw new RuntimeException("x axis value too large");
    }
    if (placedPieces[xAxis][yAxis] == 1) {
      throw new RuntimeException("space already occupied");
    }
    placedPieces[xAxis][yAxis] = 1;
  }
}
