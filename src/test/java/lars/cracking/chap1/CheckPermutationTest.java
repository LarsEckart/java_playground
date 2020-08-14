package lars.cracking.chap1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CheckPermutationTest {

  @Test
  void different_length() {
    assertThat(CheckPermutation.isPermutation("hello", "lars")).isFalse();
  }

  @Test
  void single_char_words() {
    assertThat(CheckPermutation.isPermutation("a", "a")).isTrue();
    assertThat(CheckPermutation.isPermutation("a", "b")).isFalse();
  }

  @Test
  void multiple_char_words() {
    assertThat(CheckPermutation.isPermutation("lars", "arsl")).isTrue();
  }
}
