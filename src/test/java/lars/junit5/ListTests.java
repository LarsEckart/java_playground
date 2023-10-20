package lars.junit5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ListTests {

  private static List<String> list = new ArrayList<>();

  @Test
  void testFirst() {
    List.of("123");
    list.add("one");
    assertEquals(1, list.size());
  }

  @Test
  void testSecond() {}
}
