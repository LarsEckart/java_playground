package lars;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("slow")
public class SlowTests {

  @Test
  void a_slow_test() {
    assertThat("-1").isEqualTo("-1");
  }
}
