package lars;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;

@EnabledForJreRange(max = JRE.JAVA_22)
class OhhhhMockito {

  @Test
  void default_return_of_mock() {
    MyClass mock = mock(MyClass.class);
    assertThat(mock.isAdult()).isFalse();
    assertThat(mock.isBaby()).isFalse();
    assertThat(mock.getName()).isNull();
  }

  static class MyClass {

    private boolean isAdult;
    private Boolean isBaby;
    private String name;

    public MyClass(boolean isAdult, Boolean isBaby, String name) {
      this.isAdult = isAdult;
      this.isBaby = isBaby;
      this.name = name;
    }

    public boolean isAdult() {
      return isAdult;
    }

    public Boolean isBaby() {
      return isBaby;
    }

    public String getName() {
      return name;
    }
  }
}
