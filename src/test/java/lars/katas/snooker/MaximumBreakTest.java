package lars.katas.snooker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MaximumBreakTest {

  @Test
  void getting_a_maximum_break() {
    var subject = new SnookerScorer("Alice", "Bob");
    var alice = new PlayerActor("Alice", subject);

    int times = 15;

    for (int i = 0; i < times; i++) {
      alice.pots("red");
      alice.pots("black");
    }

    alice.pots("yellow");
    alice.pots("green");
    alice.pots("brown");
    alice.pots("blue");
    alice.pots("pink");

    var result = subject.record(
        ShotBuilder
            .forPlayer("Alice")
            .hits("black")
            .pots("black")
            .shot());

    assertThat(result).isEqualTo(
        new Turn("Alice", false, (8 * times) + 2 + 3 + 4 + 5 + 6 + 7, "Alice"));

    assertThat(subject.getTotalScore("Alice")).isEqualTo((8 * times) + 2 + 3 + 4 + 5 + 6 + 7);
  }
}
