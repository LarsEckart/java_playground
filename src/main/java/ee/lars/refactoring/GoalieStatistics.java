package ee.lars.refactoring;

public class GoalieStatistics {

  private final Season season;

  public GoalieStatistics(Season season) {
    this.season = season;
  }

  public double getGoalsAgainstAverage() {
    if (season.getGames().isEmpty()) {
      return 0.0;
    } else {
      int tga = season.calculateTotalGoalsAgainst();
      double mins = season.calculateTotalMinutesPlayed();
      return (tga / mins) * 60;
    }
  }

  public double getSavePercentage() {
    if (season.getGames().isEmpty()) {
      return 0.0;
    } else {
      int g = season.calculateTotalGoalsAgainst();
      int tsoga = season.calculateTotalShotsOnGoalAgainst();
      return ((double) tsoga - g) / tsoga;
    }
  }
}
