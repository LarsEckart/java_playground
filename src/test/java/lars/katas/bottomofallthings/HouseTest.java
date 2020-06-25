package lars.katas.bottomofallthings;

import static org.assertj.core.api.Assertions.assertThat;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HouseTest {

  private House tale;

  @BeforeEach
  void setUp() {
    tale = new House();
  }

  @Test
  void test_line_1() {
    String expected = "This is the house that Jack built.\n";
    assertThat(tale.line(1)).isEqualTo(expected);
  }

  @Test
  void test_line_2() {
    String expected = "This is the malt that lay in the house that Jack built.\n";
    assertThat(tale.line(2)).isEqualTo(expected);
  }

  @Test
  void test_line_3() {
    String expected = "This is the rat that ate the malt that lay in the house that Jack built.\n";
    assertThat(tale.line(3)).isEqualTo(expected);
  }

  @Test
  void test_line_4() {
    String expected =
        "This is the cat that killed the rat that ate the malt that lay in the house that Jack built.\n";
    assertThat(tale.line(4)).isEqualTo(expected);
  }

  @Test
  void test_line_5() {
    String expected =
        "This is the dog that worried the cat that killed the rat that ate the malt that lay in the house that Jack built.\n";
    assertThat(tale.line(5)).isEqualTo(expected);
  }

  @Test
  void test_line_6() {
    String expected =
        "This is the cow with the crumpled horn that tossed the dog that worried the cat that killed the rat that ate the malt that lay in the house that Jack built.\n";
    assertThat(tale.line(6)).isEqualTo(expected);
  }

  @Test
  void test_line_7() {
    String expected =
        "This is the maiden all forlorn that milked the cow with the crumpled horn that tossed the dog that worried the cat that killed the rat that ate the malt that lay in the house that Jack built.\n";
    assertThat(tale.line(7)).isEqualTo(expected);
  }

  @Test
  void test_line_8() {
    String expected =
        "This is the man all tattered and torn that kissed the maiden all forlorn that milked the cow with the crumpled horn that tossed the dog that worried the cat that killed the rat that ate the malt that lay in the house that Jack built.\n";
    assertThat(tale.line(8)).isEqualTo(expected);
  }

  @Test
  void test_line_9() {
    String expected =
        "This is the priest all shaven and shorn that married the man all tattered and torn that kissed the maiden all forlorn that milked the cow with the crumpled horn that tossed the dog that worried the cat that killed the rat that ate the malt that lay in the house that Jack built.\n";
    assertThat(tale.line(9)).isEqualTo(expected);
  }

  @Test
  void test_line_10() {
    String expected =
        "This is the rooster that crowed in the morn that woke the priest all shaven and shorn that married the man all tattered and torn that kissed the maiden all forlorn that milked the cow with the crumpled horn that tossed the dog that worried the cat that killed the rat that ate the malt that lay in the house that Jack built.\n";
    assertThat(tale.line(10)).isEqualTo(expected);
  }

  @Test
  void test_line_11() {
    String expected =
        "This is the farmer sowing his corn that kept the rooster that crowed in the morn that woke the priest all shaven and shorn that married the man all tattered and torn that kissed the maiden all forlorn that milked the cow with the crumpled horn that tossed the dog that worried the cat that killed the rat that ate the malt that lay in the house that Jack built.\n";
    assertThat(tale.line(11)).isEqualTo(expected);
  }

  @Test
  void test_line_12() {
    String expected =
        "This is the horse and the hound and the horn that belonged to the farmer sowing his corn that kept the rooster that crowed in the morn that woke the priest all shaven and shorn that married the man all tattered and torn that kissed the maiden all forlorn that milked the cow with the crumpled horn that tossed the dog that worried the cat that killed the rat that ate the malt that lay in the house that Jack built.\n";
    assertThat(tale.line(12)).isEqualTo(expected);
  }

  @Test
  void all_the_lines() {
    Approvals.verify(tale.recite());
  }
}
