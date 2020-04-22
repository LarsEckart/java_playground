package lars.spielplatz.stateversusbehaviourverification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class Resume {

  private final List<Experience> experiences;

  public Resume() {
    experiences = new ArrayList<>();
  }

  public void addExperience(String employer, String role, LocalDate from, LocalDate until) {
    var newExperience = new Experience(employer, role, from, until);
    experiences.add(newExperience);
  }

  public Collection<Experience> experiences() {
    return List.copyOf(experiences);
  }
}
