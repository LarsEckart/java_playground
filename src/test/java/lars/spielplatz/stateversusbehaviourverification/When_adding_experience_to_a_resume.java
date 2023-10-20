package lars.spielplatz.stateversusbehaviourverification;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class When_adding_experience_to_a_resume {

  @Test
  void Then_the_specified_experience_should_now_appear_on_the_resume() {
    var experienceFrom = LocalDate.of(2014, 9, 12);
    var experienceUntil = LocalDate.of(2017, 12, 31);

    var resume = new Resume();
    resume.addExperience("Google", "Data analyst", experienceFrom, experienceUntil);

    var addedExperience =
        resume.experiences().stream()
            .findFirst()
            .orElseGet(() -> new Experience("any", "any", LocalDate.now(), LocalDate.now()));

    assertThat(addedExperience).isNotNull();
    assertThat(addedExperience.employer()).isEqualTo("Google");
    assertThat(addedExperience.role()).isEqualTo("Data analyst");
    assertThat(addedExperience.from()).isEqualTo(experienceFrom);
    assertThat(addedExperience.until()).isEqualTo(experienceUntil);
  }
}
