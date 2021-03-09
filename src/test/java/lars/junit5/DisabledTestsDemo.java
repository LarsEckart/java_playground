package lars.junit5;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.EnabledIf;

class DisabledTestsDemo {

  @Test
  @Disabled("until issue #42 is resolved")
  void never_comment_out_a_test_or_assert_statement() {}

  @Test
  @DisabledIf(value = "staticMethodThatReturnsTrue", disabledReason = "Disabled for some reason")
  void disabledOnArm() {
    assertThat(true).isEqualTo(true);
  }

  @Test
  @EnabledIf(value = "isArmArchitecture", disabledReason = "Disabled for some reason")
  void enabledOnArm() {
    assertThat(true).isEqualTo(true);
  }

  private static boolean staticMethodThatReturnsTrue() {
    return isArmArchitecture();
  }

  private static boolean isArmArchitecture() {
    return "aarch64".equals(System.getProperty("os.arch"));
  }

  private static boolean isNotArmArchitecture() {
    return !isArmArchitecture();
  }
}
