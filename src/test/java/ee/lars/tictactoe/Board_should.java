package ee.lars.tictactoe;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@RunWith(JUnitParamsRunner.class)
public class Board_should {

  private Board board;

  @Before
  public void setUp() throws Exception {
    this.board = new Board();
  }

  @Test
  @Parameters({"4", "5", "0"})
  public void not_allow_to_place_piece_outside_x_axis(int xAxis) throws Exception {
    try {
      board.placePiece(xAxis, 0);
      fail("Placing a piece outside of x axis should have thrown exception");
    } catch (RuntimeException expected) {
      assertThat(expected.getMessage()).isEqualToIgnoringCase("x axis value too large");
    }
  }

  @Test
  @Parameters({"4", "5", "0"})
  public void not_allow_to_place_piece_outside_y_axis(int yAxis) throws Exception {
    try {
      board.placePiece(1, yAxis);
      fail("Placing a piece outside of y axis should have thrown exception");
    } catch (RuntimeException expected) {
      assertThat(expected.getMessage()).isEqualToIgnoringCase("y axis value too large");
    }
  }

  @Test
  public void not_allow_to_place_piece_where_already_another_piece() throws Exception {
    board.placePiece(1, 1);

    try {
      board.placePiece(1, 1);
      fail("Placing a piece where already a piece was placed should have thrown exception");
    } catch (RuntimeException expected) {
      assertThat(expected.getMessage()).isEqualToIgnoringCase("space already occupied");
    }
  }

  @Test
  public void begin_new_game_with_player_x() throws Exception {
    board.placePiece(1, 1);

    char playerSign = board.getPlayerToken(1, 1);

    assertThat(playerSign).isEqualTo('X');
  }

  @Test
  public void make_next_turn_after_player_x_with_player_y() throws Exception {
    board.placePiece(1, 1);
    board.placePiece(2, 1);

    char playerSign = board.getPlayerToken(2, 1);

    assertThat(playerSign).isEqualTo('O');
  }

  @Ignore("false positive, was passing without changing implementation")
  @Test
  public void make_next_turn_after_player_y_with_player_x() throws Exception {
    board.placePiece(1, 1);
    board.placePiece(2, 1);
    board.placePiece(3, 1);

    char playerSign = board.getPlayerToken(3, 1);

    assertThat(playerSign).isEqualTo('X');
  }

  @Test
  public void have_no_winner_when_game_just_started() throws Exception {
    String winner = board.getWinner();

    // then
    assertThat(winner).isEqualToIgnoringCase("no winner");
  }

  @Test
  public void have_player_x_win_if_he_has_horizontal_line() throws Exception {
    board.placePiece(1,1);
    board.placePiece(1,2);
    board.placePiece(2,1);
    board.placePiece(1,3);
    board.placePiece(3,1);

    String winner = board.getWinner();

    assertThat(winner).isEqualToIgnoringCase("X");
  }
}
