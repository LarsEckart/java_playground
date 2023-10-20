package lars;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MyClass_should {

  private List<String> list = new ArrayList<>();

  @Test
  void testFirst() {
    list.add("one");
    assertEquals(1, list.size());
  }

  @Test
  void testSecond() {
    assertEquals(0, list.size());
  }
}
