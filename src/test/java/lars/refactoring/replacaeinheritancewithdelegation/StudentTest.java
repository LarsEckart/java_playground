package lars.refactoring.replacaeinheritancewithdelegation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class StudentTest {

  @Test
  void student_with_inheritance() {
    InheritanceStudent student = new InheritanceStudent("Oskar");

    assertThat(student.getName()).isEqualTo("Oskar");
  }

  @Test
  void student_with_delegation() {
    DelegationStudent student = new DelegationStudent("Oskar");

    assertThat(student.getName()).isEqualTo("Oskar");
  }
}
