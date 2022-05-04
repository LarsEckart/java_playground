package lars.katas.snooker;

import java.util.List;

public class SnookerScorer {

  private boolean oneBall;

  private final String playerOneName;
  private int playerOneScore;
  private int playerTwoScore;
  private final String playerTwoName;

  private int turns;
  private final List<String> balls;
  private boolean isPlayerOneTurn;
  private int breakScore;

  private int pottedReds;

  private boolean yellowCleared;
  private boolean greenCleared;
  private boolean onBlue;
  private boolean onPink;

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
    return this.isPlayerOneTurn ? this.playerOneName : this.playerTwoName;
  }

  public Turn record(Shot turn) {
    this.turns++;

    var reds = this.pottedReds == 15;

    turn.pottedBalls().forEach(ball -> {
      if ("red".equals(ball)) {
        this.pottedReds++;
      }
    });

    if (this.onBlue) {
      if (turn.pottedBalls().contains("white")) {
        if (!this.isPlayerOneTurn) {
          this.playerOneScore += 5;
        } else {
          this.playerTwoScore += 5;
        }
        return new Turn(!this.isPlayerOneTurn ? this.playerOneName : this.playerTwoName, true,
            this.breakScore);
      }
      if ("blue".equals(turn.hitFirst()) && turn.pottedBalls().size() == 1
          && "blue".equals(turn.pottedBalls().get(0))) {
        if (this.isPlayerOneTurn) {
          this.playerOneScore += 5;
          this.breakScore += 5;
        } else {
          this.playerTwoScore += 5;
          this.breakScore += 5;
        }
        this.onBlue = false;
        this.onPink = true;
        return new Turn(this.isPlayerOneTurn ? this.playerOneName : this.playerTwoName, false,
            this.breakScore);
      }
    } else {
      if (this.onPink) {
        if (turn.pottedBalls().contains("white")) {
          if (this.oneBall) {
            if (!this.isPlayerOneTurn) {
              this.playerOneScore += 7;
            } else {
              this.playerTwoScore += 7;
            }
            return new Turn(!this.isPlayerOneTurn ? this.playerOneName : this.playerTwoName, true,
                this.breakScore);
          } else {
            if (!this.isPlayerOneTurn) {
              this.playerOneScore += 6;
            } else {
              this.playerTwoScore += 6;
            }
            return new Turn(!this.isPlayerOneTurn ? this.playerOneName : this.playerTwoName, true,
                this.breakScore);
          }
        } else {
          if (turn.pottedBalls().size() == 1 && "pink".equals(turn.pottedBalls().get(0))
              && "pink".equals(turn.hitFirst())) {
            if (this.isPlayerOneTurn) {
              this.playerOneScore += 6;
              this.breakScore += 6;
            } else {
              this.playerTwoScore += 6;
              this.breakScore += 6;
            }
            this.oneBall = true;
            return new Turn(!this.isPlayerOneTurn ? this.playerTwoName : this.playerOneName,
                false, this.breakScore);
          }
          if (this.oneBall) {
            if (this.isPlayerOneTurn) {
              this.playerOneScore += 7;
              this.breakScore += 7;
            } else {
              this.playerTwoScore += 7;
              this.breakScore += 7;
            }
            return new Turn(!this.isPlayerOneTurn ? this.playerTwoName : this.playerOneName,
                false, this.breakScore,
                this.playerOneScore > this.playerTwoScore ? this.playerOneName
                    : this.playerTwoName);
          }
        }
      } else if (reds && !this.greenCleared && this.yellowCleared) {
        if ("green".equals(turn.hitFirst())) {
          if (turn.pottedBalls().size() == 1 && "green".equals(turn.pottedBalls().get(0))) {
            if (this.isPlayerOneTurn) {
              this.playerOneScore += 3;
              this.breakScore += 3;
            } else {
              this.playerTwoScore += 3;
              this.breakScore += 3;
            }
            this.greenCleared = true;
            return new Turn(this.isPlayerOneTurn ? this.playerOneName : this.playerTwoName, false,
                this.breakScore);
          }
        }
      }
    }

    if ("".equals(turn.hitFirst()) || turn.hitFirst() == null
        || turn.pottedBalls().contains("white")) {
      this.turns = 0;
      this.isPlayerOneTurn = !this.isPlayerOneTurn;
      if (this.isPlayerOneTurn) {
        var penalty = 4;

        if (turn.pottedBalls().contains("blue")) {
          penalty = 5;
        }
        if (turn.pottedBalls().contains("pink")) {
          penalty = 6;
        }
        if (turn.pottedBalls().contains("black")) {
          penalty = 7;
        }

        this.playerOneScore = this.playerOneScore + penalty;
      } else {
        var penalty = 4;

        if (turn.pottedBalls().contains("blue")) {
          penalty = 5;
        }
        if (turn.pottedBalls().contains("pink")) {
          penalty = 6;
        }
        if (turn.pottedBalls().contains("black")) {
          penalty = 7;
        }

        this.playerTwoScore = this.playerTwoScore + penalty;
      }
      var breakScore = this.breakScore;
      this.breakScore = 0;
      return new Turn(this.isPlayerOneTurn ? this.playerOneName : this.playerTwoName, true,
          breakScore);
    }

    if (this.greenCleared) {
      if ("brown".equals(turn.hitFirst()) && turn.pottedBalls().size() == 1
          && "brown".equals(turn.pottedBalls().get(0))) {
        this.onBlue = true;
        if (this.isPlayerOneTurn) {
          this.playerOneScore += 4;
          this.breakScore += 4;
        } else {
          this.playerTwoScore += 4;
          this.breakScore += 4;
        }
        return new Turn(!this.isPlayerOneTurn ? this.playerTwoName : this.playerOneName, false,
            this.breakScore);
      }
    }

    if (!"red".equals(turn.hitFirst()) && this.turns % 2 == 1 && !reds) {
      this.turns = 0;
      this.isPlayerOneTurn = !this.isPlayerOneTurn;
      if (this.isPlayerOneTurn) {
        var penalty = 4;

        if ("blue".equals(turn.hitFirst())) {
          penalty = 5;
        }
        if ("pink".equals(turn.hitFirst())) {
          penalty = 6;
        }
        if ("black".equals(turn.hitFirst())) {
          penalty = 7;
        }

        this.playerOneScore = this.playerOneScore + penalty;
      } else {
        var penalty = 4;

        if ("blue".equals(turn.hitFirst())) {
          penalty = 5;
        }
        if ("pink".equals(turn.hitFirst())) {
          penalty = 6;
        }
        if ("black".equals(turn.hitFirst())) {
          penalty = 7;
        }

        this.playerTwoScore = this.playerTwoScore + penalty;
      }
      var breakScore = this.breakScore;
      this.breakScore = 0;
      return new Turn(this.playerTwoName, true, breakScore);
    }

    if (turn.pottedBalls().size() == 0) {
      this.turns = 0;
      this.isPlayerOneTurn = !this.isPlayerOneTurn;
      var breakScore = this.breakScore;
      this.breakScore = 0;
      return new Turn(this.isPlayerOneTurn ? this.playerOneName : this.playerTwoName, false,
          breakScore);
    }

    if (this.turns % 2 == 0) {
      if ("red".equals(turn.hitFirst()) && !reds) {
        this.turns = 0;
        if (this.isPlayerOneTurn) {
          this.playerTwoScore += 4;
        } else {
          this.playerOneScore = this.playerOneScore + 4;
        }
        this.isPlayerOneTurn = !this.isPlayerOneTurn;
        return new Turn(this.isPlayerOneTurn ? this.playerOneName : this.playerTwoName, true, 1);
      } else if (turn.pottedBalls().size() > 1) {
        var penalty = 4;

        if (turn.pottedBalls().contains("blue")) {
          penalty = 5;
        }

        if (turn.pottedBalls().contains("pink")) {
          penalty = 6;
        }

        if (turn.pottedBalls().contains("black")) {
          penalty = 7;
        }

        if (this.isPlayerOneTurn) {
          this.playerTwoScore = this.playerTwoScore + penalty;
        } else {
          this.playerOneScore = this.playerOneScore + penalty;
        }
        this.isPlayerOneTurn = !this.isPlayerOneTurn;

        return new Turn(this.isPlayerOneTurn ? this.playerOneName : this.playerTwoName, true, 0);
      } else {
        for (var c = 1; c < this.balls.size(); c++) {
          var penalty = 0;
          if (turn.pottedBalls().contains(this.balls.get(c))
              && !this.balls.get(c).equals(turn.hitFirst())) {
            penalty = c + 1;
          }

          if (penalty > 0) {
            if (penalty < 4) {
              penalty = 4;
            }
            if (this.isPlayerOneTurn) {
              this.playerTwoScore = this.playerTwoScore + penalty;
            } else {
              this.playerOneScore = this.playerOneScore + penalty;
            }
            this.isPlayerOneTurn = !this.isPlayerOneTurn;

            return new Turn(this.isPlayerOneTurn ? this.playerOneName : this.playerTwoName, true,
                0);
          }
        }

        if (reds && turn.pottedBalls().size() == 1 && "yellow".equals(turn.pottedBalls().get(0))) {
          this.yellowCleared = true;
          if (this.isPlayerOneTurn) {
            this.playerOneScore += 2;
          } else {
            this.playerTwoScore += 2;
          }
          return new Turn(this.playerOneName, false, this.playerOneScore);
        }

        if (this.isPlayerOneTurn) {
          this.playerOneScore += (this.balls.indexOf(turn.pottedBalls().get(0)) + 1);
          this.breakScore += (this.balls.indexOf(turn.pottedBalls().get(0)) + 1);
        } else {
          this.playerTwoScore += (this.balls.indexOf(turn.pottedBalls().get(0)) + 1);
          this.breakScore += (this.balls.indexOf(turn.pottedBalls().get(0)) + 1);
        }
        return new Turn(this.playerOneName, false, this.breakScore);
      }
    } else {
      if (reds && turn.pottedBalls().size() == 1 && "yellow".equals(turn.pottedBalls().get(0))) {
        if (this.isPlayerOneTurn) {
          this.playerOneScore += 2;
          this.breakScore += 2;
        } else {
          this.playerTwoScore += 2;
          this.breakScore += 2;
        }
        this.yellowCleared = true;
        return new Turn(!this.isPlayerOneTurn ? this.playerTwoName : this.playerOneName, false,
            this.breakScore);
      }
      if (turn.pottedBalls().size() > 1) {

        var pottedColour = false;

        for (var i = 0; i < turn.pottedBalls().size(); i++) {
          if (!"red".equals(turn.pottedBalls().get(i))) {
            pottedColour = true;
            break;
          }
        }

        if (pottedColour) {

          var penalty = 4;

          if (turn.pottedBalls().contains("blue")) {
            penalty = 5;
          }

          if (turn.pottedBalls().contains("pink")) {
            penalty = 6;
          }

          if (turn.pottedBalls().contains("black")) {
            penalty = 7;
          }

          if (this.isPlayerOneTurn) {
            this.playerOneScore = this.playerOneScore + penalty;
          } else {
            this.playerTwoScore = this.playerTwoScore + penalty;
          }

          this.isPlayerOneTurn = !this.isPlayerOneTurn;
          var breakScore = this.breakScore;
          this.breakScore = 0;

          return new Turn(this.playerTwoName, true, breakScore);
        }
      }
      if (!reds) {
        if (this.isPlayerOneTurn) {
          this.playerOneScore += turn.pottedBalls().size();
          this.breakScore += turn.pottedBalls().size();
        } else {
          this.playerTwoScore += turn.pottedBalls().size();
          this.breakScore += turn.pottedBalls().size();
        }
        return new Turn(this.isPlayerOneTurn ? this.playerOneName : this.playerTwoName, false,
            this.breakScore);
      }
    }
    throw new RuntimeException("can never happen, trust me, I'm Ronnie!");
  }
}
