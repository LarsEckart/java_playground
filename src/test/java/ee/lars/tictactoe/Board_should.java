package ee.lars.tictactoe;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Fail.fail;

@RunWith(JUnitParamsRunner.class)
public class Board_should {

  @Test
  @Parameters({"4", "5", "0"})
  public void not_allow_to_place_piece_outside_x_axis(int xAxis) throws Exception {
    Board board = new Board();
    try {
      board.placePiece(xAxis, 0);
      fail("Placing a piece outside of x axis should have thrown exception");
    } catch (RuntimeException expected) {

    }
  }

  @Test
  @Parameters({"4", "5", "0"})
  public void not_allow_to_place_piece_outside_y_axis(int yAxis) throws Exception {
    Board board = new Board();
    try {
      board.placePiece(1, yAxis);
      fail("Placing a piece outside of y axis should have thrown exception");
    } catch (RuntimeException expected) {

    }
  }

  @Test
  public void not_allow_to_place_piece_where_already_another_piece() throws Exception {
    // given
    Board board = new Board();
    board.placePiece(1, 1);

    try {
      board.placePiece(1, 1);
      fail("Placing a piece where already a piece was placed should have thrown exception");
    } catch (RuntimeException expected) {

    }
  }
}
