package lars.junit5;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LifeCycle {

  @BeforeAll
  static void initAll() {}

  @BeforeEach
  void init() {}

  @Test
  void myTest() {}

  @AfterEach
  void tearDown() {}

  @AfterAll
  static void tearDownAll() {}
}
