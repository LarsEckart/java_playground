package ee.lars.refactoring;

import java.util.ArrayList;
import java.util.List;

public class Season {

  private final List<Game> games;

  public Season(List<Game> games) {
    this.games = new ArrayList<>(games);
  }

  public Season() {
    this.games = new ArrayList<>();
  }

  public void addGame(Game game) {
    games.add(game);
  }

  public void removeGame(Game game) {
    games.remove(game);
  }

  public List<Game> getGames() {
    return games;
  }

  public GoalieStatistics getGoalieStatistics() {
    return new GoalieStatistics(this);
  }
}
