package lars.katas.snooker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SnookerTest {

  SnookerScorer subject;


  @BeforeEach
  void setUp() {
    subject = new SnookerScorer("Alice", "Bob");
  }

  @Nested
  class first_shot {

    @Test
    void scores_1_point_they_pot_a_red() {
      var result = subject.record(ShotBuilder
          .forPlayer("Alice")
          .hits("red")
          .pots("red")
          .shot());

      assertThat(result).isEqualTo(new Turn("Alice", false, 1));
    }

    @Test
    void scores_no_points_and_passes_to_the_next_player_if_they_hit_a_red_but_donot_pot_it() {
      var result = subject.record(ShotBuilder
          .forPlayer("Alice")
          .hits("red")
          .shot()
      );

      assertThat(result).isEqualTo(new Turn(
          "Bob",
          false,
          0
      ));
    }

    @Test
    void scores_2_points_if_they_pot_two_red_balls() {
      var potTwoReds = ShotBuilder
          .forPlayer("Alice")
          .hits("red")
          .pots("red", "red")
          .shot();

      var result = subject.record(potTwoReds);

      assertThat(result).isEqualTo(new Turn(
          "Alice",
          false,
          2
      ));
    }
  }

  @Nested
  class after_potting_a_red_ball {

    @BeforeEach
    void setUp() {
      subject.record(ShotBuilder.potRed("Alice"));
    }

    @Test
    void potting_another_red_ball_is_a_foul() {
      var result = subject.record(ShotBuilder.potRed("Alice"));

      assertThat(result).
          isEqualTo(new Turn(
              "Bob",
              true,
              1));
    }

    @Test
    void missing_a_colour_ends_their_break() {
      var result = subject.record(ShotBuilder.forPlayer("Alice").hits("black").shot());

      assertThat(result).isEqualTo(new Turn(
          "Bob",
          false,
          1));
    }

    @Test
    void allows_their_opponent_to_score() {
      subject.record(ShotBuilder.forPlayer("Alice").hits("black").shot());

      var result = subject.record(ShotBuilder.forPlayer("Bob").hits("red").pots("red").shot());

      assertThat(result).

          isEqualTo(new Turn(
              "Bob",
              false,
              1));
    }


    static Stream<Arguments> colourScores() {
      return Stream.of(
          Arguments.of("yellow", 2),
          Arguments.of("green", 3),
          Arguments.of("brown", 4),
          Arguments.of("blue", 5),
          Arguments.of("pink", 6),
          Arguments.of("black", 7)
      );
    }

    @ParameterizedTest
    @MethodSource("colourScores")
    void potting_a_colour_is_worth_$_points_for_$_colour(String colour, int points) {

      var result = subject.record(
          ShotBuilder.forPlayer("Alice")
              .hits(colour)
              .pots(colour)
              .shot());

      assertThat(result).isEqualTo(new Turn(
          "Alice",
          false,
          1 + points));
    }
  }

  @Nested
  class break_scores_are_added_to_total_scores {

    @BeforeEach
    void setUp() {
      subject.record(ShotBuilder.potRed("Alice"));
      subject.record(ShotBuilder.potColour("Alice", "yellow"));
      subject.record(ShotBuilder.forPlayer("Alice").hits("red").shot());
      subject.record(ShotBuilder.forPlayer("Bob").hits("red").shot());
    }

    @Test
    void has_a_total_score_of_0_forBob_whohas_scored_nothing() {
      assertThat(subject.getTotalScore("Bob")).isEqualTo(0);
    }

    @Test
    void has_a_total_score_for_Alice_before_her_next_shot() {

      assertThat(subject.getTotalScore("Alice")).isEqualTo(3);
    }

    @Test
    void has_the_value_of_the_break_added_to_the_score_for_Alice() {
      subject.record(ShotBuilder.potRed("Alice"));

      assertThat(subject.getTotalScore("Alice")).isEqualTo(4);
    }
  }

  @Nested
  class some_example_games {

    @Test
    void a_game() {
      var subject = new SnookerScorer("Alice", "Bob");
      var alice = new PlayerActor("Alice", subject);
      var bob = new PlayerActor("Bob", subject);

      alice.pots("red");
      alice.pots("black");
      alice.missesRed();
      bob.pots("red");
      bob.pots("blue");
      bob.missesRed();

      alice.pots("red");

      assertThat(subject.getTotalScore("Alice")).isEqualTo(9);

      assertThat(subject.getTotalScore("Bob")).isEqualTo(6);

      assertThat(subject.getCurrentPlayer()).isEqualTo("Alice");
    }

    @Test
    void a_game_with_some_fouls() {
      var subject = new SnookerScorer("Alice", "Bob");
      var alice = new PlayerActor("Alice", subject);
      var bob = new PlayerActor("Bob", subject);

      alice.pots("red");
      alice.pots("red");

      bob.pots("red");
      bob.pots("red");

      alice.pots("red");
      alice.pots("red");

      assertThat(subject.getTotalScore("Bob")).isEqualTo(1 + 4 + 4);

      assertThat(subject.getTotalScore("Alice")).isEqualTo(1 + 1 + 4);

      assertThat(subject.getCurrentPlayer()).isEqualTo("Bob");
    }
  }
}
