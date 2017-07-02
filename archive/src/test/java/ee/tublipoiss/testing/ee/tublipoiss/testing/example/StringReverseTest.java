package ee.tublipoiss.testing.ee.tublipoiss.testing.example;

import java.util.ArrayList;
import java.util.List;

public class StringReverseTest {

    public static String reverse(String s) {
        List<String> tempArray = new ArrayList<String>(s.length());
        for (int i = 0; i < s.length(); i++) {
            tempArray.add(s.substring(i, i + 1));
        }
        StringBuilder reversedString = new StringBuilder(s.length());
        for (int i = tempArray.size() - 2; i >= 0; i--) {
            reversedString.append(tempArray.get(i));
        }
        return reversedString.toString();
    }
/*
    @Test
    public void should_reverse() {
        StringReverser stringReverser = new StringReverse();
        String original = "Oskar";
        String reversed = "raksO";

        assertThat(reverse(original)).isEqualTo(reversed);
    }
    */
}
