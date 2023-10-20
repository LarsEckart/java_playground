package lars.katas.dooskington;

import java.util.Optional;

/*
from: https://github.com/Dooskington/FighterDude2-RefactoringKata

Fighting Game Refactoring Kata
This program was designed years ago to help calculate player and NPC power levels for the new hit video game, FIGHTER DUDE 2.
It's your first day on the job and you're the new maintainer for the tool, but you don't understand how it works!
Luckily, the original Author at least left a few tests and comments behind. Maybe some refactoring is in order...
It's important that no behavior changes as a result of these refactorings!
[ ] slide statement
[ ] extract function
[ ] inline function
[ ] inline variable
[ ] introduce parameter object
[ ] split variable
[ ] replace temp with query
[ ] change function declaration
    [ ] simple mechanics
    [ ] migration mechanics
[ ] write an actual good test for calculation?
*/
public class Fight {

  public static void main(String[] args) {
    System.out.println("Hello, world!");

    // gather and display my power level
    int gear_score = 123;
    int fighting_skill_level = 15;
    int happiness = 1000;
    Optional<Integer> power_level = calculation(gear_score, fighting_skill_level, happiness);
    power_level.orElseThrow(() -> new RuntimeException("Failed to calculate"));
    System.out.println("My power level is " + power_level + "!");

    // ... fight some monsters

    // you gained experience points. fighting skill level has changed
    fighting_skill_level =
        fighting_skill_level
            + skill_level_increase_from_exp_points(exp_from_enemy(get_power_of_sun()));
    power_level = calculation(gear_score, fighting_skill_level, happiness);
    power_level.orElseThrow(() -> new RuntimeException("Failed to calculate"));
    System.out.println("My new power level is " + power_level + "!");

    Optional<Integer> enemy_power_level = calculation(5, 10, 1);
    enemy_power_level.orElseThrow(() -> new RuntimeException("Failed to calculate"));
    System.out.println("The enemy power level is " + enemy_power_level + "!");
  }

  // get the power of the sun
  public static int get_power_of_sun() {
    return 5;
  }

  // enemies give more exp if the sun is out more??
  public static int exp_from_enemy(int sun_power) {
    return 500 + (sun_power * 3);
  }

  // calculate your total power level, based on your gear score, fighting skill level, your
  // happiness, and the power of the sun and moon
  public static Optional<Integer> calculation(
      int gear_score, int fighting_skill_level, int happiness) {
    if ((gear_score < 0) || (fighting_skill_level < 0)) {
      return Optional.empty();
    }

    if (happiness < 0) {
      // being unhappy makes you evil and thus the most powerful of all
      return Optional.of(Integer.MAX_VALUE);
    }

    // calculate fighting power based on gear score
    int fighting_power = 0;
    for (int i = 0; i <= gear_score; i++) {
      fighting_power += (fighting_skill_level * 2) + i;
    }

    // true happiness actually comes from your fighting power.
    happiness = happiness + (fighting_power / 3);

    int moon_power = 5 * 2;
    int sun_power = get_power_of_sun() + happiness;

    return Optional.of((fighting_power + moon_power + sun_power));
  }

  public static int skill_level_increase_from_exp_points(int exp) {
    return (int) (exp + Math.floor((exp * exp) * 0.1));
  }
}
