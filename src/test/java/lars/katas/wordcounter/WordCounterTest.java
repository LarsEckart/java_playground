package lars.katas.wordcounter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class WordCounterTest {

  @Test
  void singleWordIsCounted() {
    assertThat(new WordCounter().counts("happy")).isEqualTo("happy=1");
  }

  @Test
  void repeatedWordIsCounted() {
    assertThat(new WordCounter().counts("happy happy")).isEqualTo("happy=2");
  }
}
