package lars.refactoring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
class PatternTest {

  @Test
  void doesNotChangeUntaggedText() {
    assertEquals("plain text", Pattern.formatText("plain text"));
  }

  @Test
  void removesTextBetweenAngleBracketPairs() {
    assertEquals("", Pattern.formatText("<>"));
  }

  @Test
  void approvals() {
    String[] input = new String[] {null, "", "plain text", "<>", "this is <b>important</b>"};
    Approvals.verifyAll("Stripping html tags", input, a -> testFormatText(a));
  }

  private String testFormatText(String input) {
    String result;
    try {
      result = Pattern.formatText(input);
    } catch (Exception e) {
      result = e.getClass().getName();
    }
    return input + " => " + result;
  }
}
