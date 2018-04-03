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
      int totalGoalsAgainst = season.calculateTotalGoalsAgainst();
      double totalMinutesPlayed = season.calculateTotalMinutesPlayed();
      return (totalGoalsAgainst / totalMinutesPlayed) * 60;
    }
  }

  public double getSavePercentage() {
    if (season.getGames().isEmpty()) {
      return 0.0;
    } else {
      int totalGoalsAgainst = season.calculateTotalGoalsAgainst();
      int totalShotsOnGoalAgainst = season.calculateTotalShotsOnGoalAgainst();
      return ((double) totalShotsOnGoalAgainst - totalGoalsAgainst) / totalShotsOnGoalAgainst;
    }
  }
}
