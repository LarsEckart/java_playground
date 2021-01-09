package lars.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PatternMatcher_should {

  @Test
  void match_positive_and_negative_integers() {
    Pattern pattern = Pattern.compile("-?\\d+");
    Matcher matcher = pattern.matcher("1;-2;3,4.5,11");

    List<String> numbers = new ArrayList<>();
    while (matcher.find()) {
      numbers.add(matcher.group());
    }

    assertThat(numbers).isEqualTo(List.of("1", "-2", "3", "4", "5", "11"));
  }

  @Test
  void test_single_quotes() {
    Pattern pattern = Pattern.compile("createdAt='[^']+'");
    Matcher matcher = pattern.matcher("<orderHistory createdAt='2021-01-09T09:08Z'>");

    assertThat(matcher.find()).isTrue();
  }
}
