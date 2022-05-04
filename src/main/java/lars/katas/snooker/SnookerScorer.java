package lars.katas.snooker;

import java.util.List;

public class SnookerScorer {

  boolean oneBall;

  String playerOneName;
  int playerOneScore;
  int playerTwoScore;
  String playerTwoName;

  int turns;
  List<String> balls;
  boolean isPlayerOneTurn;
  int breakScore;

  int pottedReds;

  boolean yellowCleared;
  boolean greenCleared;
  boolean onBlue;
  boolean onPink;


  public SnookerScorer(String playerOneName, String playerTwoName) {
    this.playerOneName = playerOneName;
    this.playerTwoName = playerTwoName;

    this.playerOneScore = 0;
    this.playerTwoScore = 0;

    this.turns = 0;
    this.pottedReds = 0;
    this.breakScore = 0;
    this.isPlayerOneTurn = true;
    this.yellowCleared = false;
    this.greenCleared = false;
    this.onPink = false;

    this.balls = List.of("red", "yellow", "green", "brown", "blue", "pink", "black");
  }

  int getTotalScore(String playerName) {
    if (playerName.equals(this.playerOneName)) {
      return this.playerOneScore;
    }

    return this.playerTwoScore;
  }

  String getCurrentPlayer() {
    return this.isPlayerOneTurn ? this.playerOneName :
        this.playerTwoName;
  }

  public Turn record(Shot shot) {
    return new Turn("", false, -1);
  }
}
