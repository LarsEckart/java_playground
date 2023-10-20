package lars.refactoring;

import static lars.refactoring.ExampleConditionals.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
class ExampleConditionalsTest {

  @Test
  void test_de_morgan() {
    assertThat(deMorgan(1)).isFalse();
    assertThat(deMorgan(5)).isTrue();
    assertThat(deMorgan(7)).isTrue();
  }

  @Test
  void test_normalize() {
    assertThat(normalize("hello", "world")).isEqualTo(5);
    assertThat(normalize("bar", "foo")).isEqualTo(6);
    assertThat(normalize("foo", "world")).isEqualTo(0);
    assertThat(normalize("foo", "bar")).isEqualTo(1);
    assertThat(normalize("bar", "world")).isEqualTo(2);
    assertThat(normalize("hello", "bar")).isEqualTo(3);
    assertThat(normalize("hello", "foo")).isEqualTo(4);
  }

  @Test
  void test_split() {
    assertThat(split(3, 4)).isEqualTo(7);
    assertThat(split(3, 5)).isEqualTo(0);
  }

  @Test
  void test_join() {
    assertThat(join(3, 4)).isEqualTo(7);
    assertThat(join(3, 5)).isEqualTo(0);
  }

  @Test
  void test_split_statements() {
    assertThat(split_statements(4, 1)).isEqualTo(12);
    assertThat(split_statements(3, 1)).isEqualTo(0);
    assertThat(split_statements(4, 2)).isEqualTo(24);
  }

  @Test
  void test_join_statements() {
    assertThat(join_statements(4, 1)).isEqualTo(12);
    assertThat(join_statements(3, 1)).isEqualTo(0);
    assertThat(join_statements(4, 2)).isEqualTo(24);
  }

  @Test
  void test_redundant_else() {
    assertThat(redundant_else(0)).isEqualTo(1);
    assertThat(redundant_else(3)).isEqualTo(10);
    assertThat(redundant_else(10)).isEqualTo(30);
    assertThat(redundant_else(30)).isEqualTo(0);
    assertThat(redundant_else(40)).isEqualTo(0);
  }

  @Test
  void test_invert() {
    assertThat(invert(3)).isEqualTo(4);
    assertThat(invert(4)).isEqualTo(3);
  }
}
