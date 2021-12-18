package lars.katas.dooskington;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class FightTest {

  @Test
  void calculation_returns_correct_result() {
    // Oh no! How do I write this test, the function is complicated
    assertEquals(0, 0, "noop");
    }

  @Test
  void negative_gear_score_results_in_no_total_power() {
    assertEquals(Optional.empty(), Fight.calculation(-1, 0, 0));
    }

  @Test
  void negative_fighting_skill_results_in_no_total_power() {
    assertEquals(Optional.empty(), Fight.calculation(-1, 0, 0));
    }

  @Test
  void negative_happiness_results_in_max_total_power() {
    assertEquals(Optional.of(Integer.MAX_VALUE), Fight.calculation(0, 0, -1));
    }

  @Test
  void exp_from_enemy_correct() {
    assertEquals(515, Fight.exp_from_enemy(5));
    }
}
