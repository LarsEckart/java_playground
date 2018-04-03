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
      int tga = 0;
      double mins = 0;
      for (Game game : games) {
        tga += game.getGoalsAgainst();
        mins += game.getMinutesPlayed();
      }
      for (Game game : games) {
        tga += game.getGoalsAgainst();
        mins += game.getMinutesPlayed();
      }
      return (tga / mins) * 60;
    }
  }

  public double getSavePercentage() {
    if (season.getGames().isEmpty()) {
      return 0.0;
    } else {
      List<Game> games = season.getGames();
      int g = 0;
      int tsoga = 0;
      for (Game game : games) {
        g += game.getGoalsAgainst();
        tsoga += game.getShotsOnGoalAgainst();
      }
      return ((double) tsoga - g) / tsoga;
    }
  }
}
