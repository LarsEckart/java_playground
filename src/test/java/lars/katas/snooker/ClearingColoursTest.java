package lars.katas.snooker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClearingColoursTest {

  SnookerScorer subject = new SnookerScorer("Alice", "Bob");
  PlayerActor alice = new PlayerActor("Alice", subject);
  PlayerActor bob = new PlayerActor("Bob", subject);

  @BeforeEach
  void after_clearing_all_the_red_balls() {
    subject = new SnookerScorer("Alice", "Bob");
    alice = new PlayerActor("Alice", subject);
    bob = new PlayerActor("Bob", subject);

    for (int i = 0; i < 15; i++) {
      alice.pots("red");
      alice.pots("black");
    }
  }

  @Test
  void the_yellow_must_be_potted_first() {
    var result = subject.record(ShotBuilder
        .forPlayer("Alice")
        .hits("yellow")
        .pots("yellow")
        .shot());

    assertThat(result).isEqualTo(new Turn("Alice", false, (8 * 15) + 2));
    assertThat(subject.getTotalScore("Alice")).isEqualTo((8 * 15) + 2);

  }


  @Test
  void if_Alice_misses_the_yellow_it_is_on_for_Bob() {
    subject.record(ShotBuilder
        .forPlayer("Alice")
        .hits("yellow")
        .shot());

    var result = subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("yellow")
        .pots("yellow")
        .shot());

    assertThat(result).isEqualTo(new Turn("Bob", false, 2));

    assertThat(subject.getTotalScore("Bob")).isEqualTo(2);
  }


  @Test
  void the_green_must_be_potted_after_the_yellow() {
    subject.record(ShotBuilder
        .forPlayer("Alice")
        .hits("yellow")
        .shot());

    subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("yellow")
        .pots("yellow")
        .shot());

    var result = subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("green")
        .pots("green")
        .shot());

    assertThat(result).isEqualTo(new Turn("Bob", false, 5));
    assertThat(subject.getTotalScore("Bob")).isEqualTo(5);
  }

  @Test
  void the_green_can_be_potted_by_Alice_if_Bob_misses_it() {
    subject.record(ShotBuilder
        .forPlayer("Alice")
        .hits("yellow")
        .shot());

    subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("yellow")
        .pots("yellow")
        .shot());

    subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("green")
        .shot());

    var result = subject.record(ShotBuilder
        .forPlayer("Alice")
        .hits("green")
        .pots("green")
        .shot());

    assertThat(result).isEqualTo(new Turn(
        "Alice",
        false,
        3));

    assertThat(subject.getTotalScore("Alice")).isEqualTo(120 + 3);
  }

  @Test
  void the_brown_must_be_potted_after_the_green() {
    subject.record(ShotBuilder
        .forPlayer("Alice")
        .hits("yellow")
        .shot());

    subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("yellow")
        .pots("yellow")
        .shot());

    subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("green")
        .pots("green")
        .shot());

    var result = subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("brown")
        .pots("brown")
        .shot());

    assertThat(result).isEqualTo(new Turn(
        "Bob",
        false,
        5 + 4));

    assertThat(subject.getTotalScore("Bob")).isEqualTo(5 + 4);
  }

  @Test
  void the_blue_must_be_potted_after_the_brown() {
    subject.record(ShotBuilder
        .forPlayer("Alice")
        .hits("yellow")
        .shot());

    subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("yellow")
        .pots("yellow")
        .shot());

    subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("green")
        .pots("green")
        .shot());

    subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("brown")
        .pots("brown")
        .shot());

    var result = subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("blue")
        .pots("blue")
        .shot());

    assertThat(result).isEqualTo(new Turn(
        "Bob",
        false,
        9 + 5));

    assertThat(subject.getTotalScore("Bob")).isEqualTo(9 + 5);
  }

  @Test
  void the_pink_must_be_potted_after_the_blue() {
    subject.record(ShotBuilder
        .forPlayer("Alice")
        .hits("yellow")
        .shot());

    subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("yellow")
        .pots("yellow")
        .shot());

    subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("green")
        .pots("green")
        .shot());

    subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("brown")
        .pots("brown")
        .shot());

    subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("blue")
        .pots("blue")
        .shot());

    var result = subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("pink")
        .pots("pink")
        .shot());

    assertThat(result).isEqualTo(new Turn(
        "Bob",
        false,
        14 + 6));

    assertThat(subject.getTotalScore("Bob")).isEqualTo(14 + 6);
  }

  @Test
  void the_black_must_be_potted_after_the_pink() {
    subject.record(ShotBuilder
        .forPlayer("Alice")
        .hits("yellow")
        .shot());

    subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("yellow")
        .pots("yellow")
        .shot());

    subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("green")
        .pots("green")
        .shot());

    subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("brown")
        .pots("brown")
        .shot());

    subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("blue")
        .pots("blue")
        .shot());

    subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("pink")
        .pots("pink")
        .shot());

    var result = subject.record(ShotBuilder
        .forPlayer("Bob")
        .hits("black")
        .pots("black")
        .shot());

    assertThat(result).isEqualTo(new Turn(
        "Bob",
        false,
        20 + 7,
        "Alice"));

    assertThat(subject.getTotalScore("Bob")).isEqualTo(20 + 7);
  }
}
