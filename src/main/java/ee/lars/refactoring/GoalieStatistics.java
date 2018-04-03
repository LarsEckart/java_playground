package ee.lars.refactoring;

import java.util.List;

public class GoalieStatistics {

  private final Season season;

  public GoalieStatistics(Season season) {
    this.season = season;
  }

  public double getGoalsAgainstAverage() {
    if (season.getGames().isEmpty()) {
      return 0.0;
    } else {
      List<Game> games = season.getGames();
      int tga = season.calculateTotalGoalsAgainst(games);
      double mins = season.calculateTotalMinutesPlayed(games);
      return (tga / mins) * 60;
    }
  }

  public double getSavePercentage() {
    if (season.getGames().isEmpty()) {
      return 0.0;
    } else {
      List<Game> games = season.getGames();
      int g = season.calculateTotalGoalsAgainst(games);
      int tsoga = season.calculateTotalShotsOnGoalAgainst(games);
      return ((double) tsoga - g) / tsoga;
    }
  }
}
