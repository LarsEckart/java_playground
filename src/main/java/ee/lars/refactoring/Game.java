package ee.lars.refactoring;

public class Game {

  private final double minutesPlayed;
  private final int goalsAgainst;
  private final int shotsOnGoalAgainst;

  public Game(int goalsAgainst, int shotsOnGoalAgainst, double minutesPlayed) {
    this.goalsAgainst = goalsAgainst;
    this.shotsOnGoalAgainst = shotsOnGoalAgainst;
    this.minutesPlayed = minutesPlayed;
  }

  public int getGoalsAgainst() {
    return goalsAgainst;
  }

  public int getShotsOnGoalAgainst() {
    return shotsOnGoalAgainst;
  }

  public double getMinutesPlayed() {
    return minutesPlayed;
  }
}
