package lars.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PatternMatcher_should {

    @Test
    public void match_positive_and_negative_integers() throws Exception {
        Pattern pattern = Pattern.compile("-?\\d+");
        Matcher matcher = pattern.matcher("1;-2;3,4.5,11");

        List<String> numbers = new ArrayList<>();
        while (matcher.find()) {
            numbers.add(matcher.group());
        }

        assertThat(numbers).isEqualTo(List.of("1", "-2", "3", "4", "5", "11"));
    }
}
