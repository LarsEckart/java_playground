package ee.lars.tictactoe;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@RunWith(JUnitParamsRunner.class)
public class
TicTacToe_should {

  private TicTacToe ticTacToe;

  @Before
  public void setUp() throws Exception {
    this.ticTacToe = new TicTacToe();
  }

  @Test
  @Parameters({"4", "5", "0"})
  public void not_allow_to_place_piece_outside_possible_rows(int row) throws Exception {
    try {
      ticTacToe.placePiece(row, 1);
      fail("Placing a piece outside of allowed rows should have thrown exception");
    } catch (IllegalArgumentException expected) {
      assertThat(expected.getMessage()).isEqualToIgnoringCase("not a valid row");
    }
  }

  @Test
  @Parameters({"4", "5", "0"})
  public void not_allow_to_place_piece_outside_possible_columns(int column) throws Exception {
    try {
      ticTacToe.placePiece(1, column);
      fail("Placing a piece outside of allowed columns should have thrown exception");
    } catch (IllegalArgumentException expected) {
      assertThat(expected.getMessage()).isEqualToIgnoringCase("not a valid column");
    }
  }

  @Test
  public void not_allow_to_place_piece_where_already_another_piece() throws Exception {
    ticTacToe.placePiece(1, 1);

    try {
      ticTacToe.placePiece(1, 1);
      fail("Placing a piece where already a piece was placed should have thrown exception");
    } catch (IllegalArgumentException expected) {
      assertThat(expected.getMessage()).isEqualToIgnoringCase("space already occupied");
    }
  }

  @Test
  public void begin_new_game_with_player_x() throws Exception {
    ticTacToe.placePiece(1, 1);

    char playerSign = ticTacToe.getPlayerToken(1, 1);

    assertThat(playerSign).isEqualTo('X');
  }

  @Test
  public void make_next_turn_after_player_x_with_player_y() throws Exception {
    ticTacToe.placePiece(1, 1);
    ticTacToe.placePiece(2, 1);

    char playerSign = ticTacToe.getPlayerToken(2, 1);

    assertThat(playerSign).isEqualTo('O');
  }

  @Ignore("false positive, was passing without changing implementation")
  @Test
  public void make_next_turn_after_player_y_with_player_x() throws Exception {
    ticTacToe.placePiece(1, 1);
    ticTacToe.placePiece(2, 1);
    ticTacToe.placePiece(3, 1);

    char playerSign = ticTacToe.getPlayerToken(3, 1);

    assertThat(playerSign).isEqualTo('X');
  }

  @Test
  public void have_no_winner_when_game_just_started() throws Exception {
    String winner = ticTacToe.getWinner();

    // then
    assertThat(winner).isEqualToIgnoringCase("no winner");
  }

  @Test
  public void have_player_X_win_if_he_has_horizontal_line() throws Exception {
    ticTacToe.placePiece(1,1);
    ticTacToe.placePiece(2,2);
    ticTacToe.placePiece(1,2);
    ticTacToe.placePiece(3,3);
    ticTacToe.placePiece(1,3);

    String winner = ticTacToe.getWinner();

    assertThat(winner).isEqualToIgnoringCase("X wins");
  }

  @Test
  public void have_player_O_win_if_she_has_horizontal_line() throws Exception {
    ticTacToe.placePiece(1,1);
    ticTacToe.placePiece(2,1);
    ticTacToe.placePiece(3,1);
    ticTacToe.placePiece(2,2);
    ticTacToe.placePiece(3,3);
    ticTacToe.placePiece(2,3);

    String winner = ticTacToe.getWinner();

    assertThat(winner).isEqualToIgnoringCase("O wins");
  }

  @Test
  public void have_player_X_win_if_he_has_vertical_line() throws Exception {
    ticTacToe.placePiece(1,1);
    ticTacToe.placePiece(2,2);
    ticTacToe.placePiece(2,1);
    ticTacToe.placePiece(3,3);
    ticTacToe.placePiece(3,1);

    String winner = ticTacToe.getWinner();

    assertThat(winner).isEqualToIgnoringCase("X wins");
  }

  @Test
  public void have_player_O_win_if_he_has_vertical_line() throws Exception {
    ticTacToe.placePiece(2,2);
    ticTacToe.placePiece(1,1);
    ticTacToe.placePiece(3,3);
    ticTacToe.placePiece(2,1);
    ticTacToe.placePiece(3,2);
    ticTacToe.placePiece(3,1);

    String winner = ticTacToe.getWinner();

    assertThat(winner).isEqualToIgnoringCase("O wins");
  }
}
