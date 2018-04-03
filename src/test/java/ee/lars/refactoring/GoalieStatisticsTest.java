package ee.lars.refactoring;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GoalieStatisticsTest {

  private static final double TOLERANCE = 0.000001;
  private Season season;

  @Before
  public void setUp() {
    season = new Season();
  }

  @Test
  public void noGamesInSeasonEnsureZeroGoalsAgainstAverage() {
    GoalieStatistics statistics = season.getGoalieStatistics();
    assertEquals(0.0, statistics.getGoalsAgainstAverage(), TOLERANCE);
  }

  @Test
  public void noGamesInSeasonEnsureZeroSavePercentage() {
    GoalieStatistics statistics = season.getGoalieStatistics();
    assertEquals(0.0, statistics.getSavePercentage(), TOLERANCE);
  }

  @Test
  public void oneGameInSeasonEnsureCorrectGoaliesAgainstAverage() {
    season.addGame(new Game(2, 20, 60.0));
    GoalieStatistics statistics = season.getGoalieStatistics();
    assertEquals(2.0, statistics.getGoalsAgainstAverage(), TOLERANCE);
  }

  @Test
  public void oneGameInSeasonEnsureCorrectSavePercentage() {
    season.addGame(new Game(2, 20, 60.0));
    GoalieStatistics statistics = season.getGoalieStatistics();
    assertEquals(0.90, statistics.getSavePercentage(), TOLERANCE);
  }

  @Test
  public void twoGameInSeasonEnsureCorrectGoaliesAgainstAverage() {
    season.addGame(new Game(2, 20, 60.0));
    season.addGame(new Game(3, 40, 60.0));
    GoalieStatistics statistics = season.getGoalieStatistics();
    assertEquals(2.5, statistics.getGoalsAgainstAverage(), TOLERANCE);
  }

  @Test
  public void twoGameInSeasonEnsureCorrectSavePercentage() {
    season.addGame(new Game(2, 20, 60.0));
    season.addGame(new Game(3, 40, 60.0));
    GoalieStatistics statistics = season.getGoalieStatistics();
    assertEquals(0.9167, round4Decimals(statistics.getSavePercentage()), TOLERANCE);
  }

  private static double round4Decimals(double value) {
    return (double) Math.round(value * 10000) / 10000;
  }

  @Test
  public void onePartialGameInSeasonEnsureCorrectGoalsAgainstAverage() {
    season.addGame(new Game(2, 20, 30.0));
    GoalieStatistics statistics = season.getGoalieStatistics();
    assertEquals(4.0, statistics.getGoalsAgainstAverage(), TOLERANCE);
  }
}
