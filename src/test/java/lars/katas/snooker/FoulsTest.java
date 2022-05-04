package lars.katas.snooker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class FoulsTest {

  SnookerScorer subject;

  @BeforeEach
  void setUp() {
    subject = new SnookerScorer("Alice", "Bob");
  }

  @Nested
  class on_a_red_ball {

    @Test
    void is_a_foul_if_they_fail_to_hit_a_ball() {
      var result = subject.record(ShotBuilder
          .forPlayer("Alice")
          .shot());

      assertThat(result).isEqualTo(new Turn(
          "Bob",
          true,
          0));
    }

    @Test
    void after_failing_to_hit_a_red_ball_the_turn_passes_to_their_opponent() {
      subject.record(ShotBuilder
          .forPlayer("Alice")
          .shot());

      var result = subject.record(ShotBuilder
          .forPlayer("Bob")
          .hits("red")
          .pots("red")
          .shot());

      assertThat(result).isEqualTo(new Turn(
          "Bob",
          false,
          1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"yellow", "green", "brown", "blue", "pink", "black"})
    void is_a_foul_if_they_hit_a_coloured_ball_before_the_red(String ball) {
      subject = new SnookerScorer("Alice", "Bob"); // TODO remove?

      var result = subject.record(ShotBuilder
          .forPlayer("Alice")
          .hits(ball)
          .shot());

      assertThat(result).isEqualTo(new Turn(
          "Bob",
          true,
          0));
    }

    @Nested
    class is_a_foul_if_they_pot_any_coloured_ball {

      static Stream<Arguments> colours() {
        return Stream.of(
            Arguments.of("yellow"),
            Arguments.of("green"),
            Arguments.of("brown"),
            Arguments.of("blue"),
            Arguments.of("pink"),
            Arguments.of("black")
        );
      }

      @ParameterizedTest
      @MethodSource("colours")
      void first(String ball) {
        subject = new SnookerScorer("Alice", "Bob");

        var result = subject.record(ShotBuilder
            .forPlayer("Alice")
            .hits("red")
            .pots(ball)
            .pots("red")
            .shot());

        assertThat(result).isEqualTo(new Turn(
            "Bob",
            true,
            0));
      }

      @ParameterizedTest
      @MethodSource("colours")
      void second(String ball) {
        subject = new SnookerScorer("Alice", "Bob");

        var result = subject.record(ShotBuilder
            .forPlayer("Alice")
            .hits("red")
            .pots("red")
            .pots(ball)
            .shot());

        assertThat(result).isEqualTo(new Turn(
            "Bob",
            true,
            0));
      }

      @ParameterizedTest
      @MethodSource("colours")
      void third(String ball) {

        subject = new SnookerScorer("Alice", "Bob");

        var result = subject.record(ShotBuilder
            .forPlayer("Alice")
            .hits("red")
            .pots("red")
            .pots("red")
            .pots(ball)
            .shot());

        assertThat(result).isEqualTo(new Turn(
            "Bob",
            true,
            0));
      }
    }

    @Nested
    class is_a_foul_if_they_pot_the_white_ball {

      @Test
      void as_the_first_ball() {
        var result = subject.record(ShotBuilder
            .forPlayer("Alice")
            .hits("red")
            .pots("white")
            .pots("red")
            .shot());

        assertThat(result).isEqualTo(new Turn(
            "Bob",
            true,
            0));
      }

      @Test
      void as_the_second_ball() {
        var result = subject.record(ShotBuilder
            .forPlayer("Alice")
            .hits("red")
            .pots("red")
            .pots("white")
            .shot());

        assertThat(result).isEqualTo(new Turn(
            "Bob",
            true,
            0));
      }

      @Test
      void as_the_only_ball() {
        var result = subject.record(ShotBuilder
            .forPlayer("Alice")
            .hits("red")
            .pots("white")
            .shot());

        assertThat(result).isEqualTo(new Turn(
            "Bob",
            true,
            0));
      }
    }
  }

  @Nested
  class on_a_colour_potting_a_different_colour_to_the_one_you_hit_first_is_a_foul_a_minimum_of_4 {

    static Stream<Arguments> colourPenalty() {
      return Stream.of(
          Arguments.of("green", 4),
          Arguments.of("brown", 4),
          Arguments.of("blue", 5),
          Arguments.of("pink", 6),
          Arguments.of("black", 7)
      );
    }

    @ParameterizedTest
    @MethodSource("colourPenalty")
      // todo fix test name with dynamic variable
    void is_a_foul_of_points_for_potting_the_colour_after_hitting_the_yellow(String colour,
        int points) {

      var subject = new SnookerScorer("Alice", "Bob");

      subject.record(ShotBuilder
          .forPlayer("Alice")
          .hits("red")
          .pots("red")
          .shot());

      subject.record(ShotBuilder
          .forPlayer("Alice")
          .hits("yellow")
          .pots(colour)
          .shot());

      assertThat(subject.getTotalScore("Bob")).isEqualTo(points);
    }
  }

  @Nested
  class fouling_on_a_colour_gives_4points_or_the_value_of_the_colour_if_higher {

    static Stream<Arguments> colourPenalty() {
      return Stream.of(
          Arguments.of("blue", 5),
          Arguments.of("pink", 6),
          Arguments.of("black", 7)
      );
    }

    @ParameterizedTest
    @MethodSource("colourPenalty")
    void hitting_a_colour_instead_of_a_red_when_it_is_the_players_first_shot_is_a_foul_of_$_points_for_the_colour(
        String colour, int points) {

      var subject = new SnookerScorer("Alice", "Bob");

      subject.record(ShotBuilder
          .forPlayer("Alice")
          .hits(colour)
          .pots("red")
          .shot());

      assertThat(subject.getTotalScore("Bob")).isEqualTo(points);
    }

    @ParameterizedTest
    @MethodSource("colourPenalty")
    void potting_a_colour_worth_more_than_4_and_going_in_off_with_the_white_is_a_foul_of_$_points_for_the_$_colour(
        String colour, int points) {

      var subject = new SnookerScorer("Alice", "Bob");

      subject.record(ShotBuilder
          .forPlayer("Alice")
          .hits("red")
          .pots("red")
          .shot());

      subject.record(ShotBuilder
          .forPlayer("Alice")
          .hits(colour)
          .pots(colour)
          .pots("white")
          .shot()
      );

      assertThat(subject.getTotalScore("Bob")).isEqualTo(points);
    }

    @ParameterizedTest
    @MethodSource("colourPenalty")
    void potting_more_than_one_colour_when_a_colour_is_on_is_a_foul_of_$_points_for_the_$_colour(
        String colour, int points) {
      var subject = new SnookerScorer("Alice", "Bob");

      subject.record(ShotBuilder
          .forPlayer("Alice")
          .hits("red")
          .pots("red")
          .shot());

      subject.record(ShotBuilder
          .forPlayer("Alice")
          .hits("yellow")
          .pots("yellow")
          .pots(colour)
          .shot());

      assertThat(subject.getTotalScore("Bob")).isEqualTo(points);
    }
  }


  @Nested
  class potting_the_white_ball_gives_four_points_to_the_opponent {

    @Test
    void as_the_first_player() {
      subject.record(ShotBuilder.potColour("Alice", "white"));

      assertThat(subject.getTotalScore("Bob")).

          isEqualTo(4);
    }

    @Test
    void as_the_second_player() {
      subject.record(ShotBuilder.potColour("Alice", "white"));
      subject.record(ShotBuilder.potColour("Bob", "white"));

      assertThat(subject.getTotalScore("Alice")).isEqualTo(4);
    }
  }

  @Nested
  class the_minimum_score_for_a_foul_increases_if_all_other_balls_before_the_blue_have_been_potted {

    SnookerScorer subject = new SnookerScorer("Alice", "Bob");
    PlayerActor alice = new PlayerActor("Alice", subject);
    PlayerActor bob = new PlayerActor("Bob", subject);

    @BeforeEach
    void setUp() {
      alice.missesRed();

      for (int i = 0; i < 15; i++) {
        bob.pots("red");
        bob.pots("black");
      }

      bob.pots("yellow");
      bob.pots("green");
      bob.pots("brown");
    }

    @Test
    void is_5_points_for_potting_the_white_when_on_the_blue() {
      var result = subject.record(ShotBuilder
          .forPlayer("Bob")
          .pots("white").shot());

      assertThat(result).isEqualTo(new Turn(
          "Alice",
          true,
          (15 * 8) + 2 + 3 + 4));

      assertThat(subject.getTotalScore("Alice")).

          isEqualTo(5);
    }

    @Test
    void is_6_points_for_potting_the_white_when_on_the_pink() {

      bob.pots("blue");

      var result = subject.record(ShotBuilder
          .forPlayer("Bob")
          .pots("white").shot());

      assertThat(result).isEqualTo(new Turn(
          "Alice",
          true,
          (15 * 8) + 2 + 3 + 4 + 5));

      assertThat(subject.getTotalScore("Alice")).isEqualTo(6);
    }

    @Test
    void is_7_points_for_potting_the_white_when_on_the_black() {

      bob.pots("blue");
      bob.pots("pink");

      var result = subject.record(ShotBuilder
          .forPlayer("Bob")
          .pots("white").shot());

      assertThat(result).isEqualTo(new Turn(
          "Alice",
          true,
          (15 * 8) + 2 + 3 + 4 + 5 + 6));

      assertThat(subject.getTotalScore("Alice")).isEqualTo(7);
    }
  }

}
