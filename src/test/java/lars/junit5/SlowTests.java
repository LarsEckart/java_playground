package lars.junit5;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("slow")
public class SlowTests {

  @Test
  @Tag("slow")
  void a_slow_test1() throws Exception {
    Thread.sleep(3000);
    assertThat("-1").isEqualTo("-1");
  }

  @Test
  void a_test2() throws Exception {
    assertThat("-1").isEqualTo("-1");
  }

  @Test
  @Tag("slow")
  void a_slow_test3() throws Exception {
    Thread.sleep(3000);
    assertThat("-1").isEqualTo("-1");
  }

  @Test
  void a_test4() throws Exception {
    assertThat("-1").isEqualTo("-1");
  }
}
