package ee.lars.refactoring;

public class GoalieStatistics {

  private final Season season;

  public GoalieStatistics(Season season) {
    this.season = season;
  }

  public double getGoalsAgainstAverage() {
    if (season.hasStarted()) {
      int totalGoalsAgainst = season.calculateTotalGoalsAgainst();
      double totalMinutesPlayed = season.calculateTotalMinutesPlayed();
      return (totalGoalsAgainst / totalMinutesPlayed) * 60;
    } else {
      return 0.0;
    }
  }

  public double getSavePercentage() {
    if (season.hasStarted()) {
      int totalGoalsAgainst = season.calculateTotalGoalsAgainst();
      int totalShotsOnGoalAgainst = season.calculateTotalShotsOnGoalAgainst();
      return ((double) totalShotsOnGoalAgainst - totalGoalsAgainst) / totalShotsOnGoalAgainst;
    } else {
      return 0.0;
    }
  }
}
