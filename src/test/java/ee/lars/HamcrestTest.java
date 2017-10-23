package ee.lars;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

public class HamcrestTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void is_equal_to() throws Exception {
        assertThat(7 * 6, is(42));
        assertThat(7 * 6, equalTo(42));
        assertThat(7 * 6, is(equalTo(42)));
    }

    @Test
    public void instance_of_example() throws Exception {
        assertThat(Long.valueOf(1), instanceOf(Long.class));
        assertThat(Long.valueOf(1), is(instanceOf(Long.class)));

        // assertThat(Long.valueOf(1), is(instanceOf(Integer.class)));
        // AssertionError:
        // Expected: is an instance of java.lang.Integer
        // but: <1L> is a java.lang.Long
    }

    @Test
    public void list_matchers() throws Exception {
        List<Integer> list = Arrays.asList(1, 3, 5, 7, 9);

        assertThat(list, hasSize(5));
        assertThat(list, hasItem(7));
        assertThat(list, contains(1, 3, 5, 7, 9));
        assertThat(list, containsInAnyOrder(1, 9, 3, 7, 5));
        assertThat(list, everyItem(greaterThan(0)));
    }

    @Test
    public void all_of_example() throws Exception {
        assertThat(42, allOf(is(greaterThan(7)), is(greaterThan(30))));
        assertThat("hello world", anyOf(is("hello"), containsString("world")));
    }

    @Test
    public void not_example() throws Exception {
        assertThat(Long.valueOf(1), not(Integer.class));
        assertThat(Long.valueOf(1), is(not(Integer.class)));
        assertThat(42, is(not(equalTo(7 * 7))));
    }

    @Test
    public void map_matchers() throws Exception {
        Map<String, String> myMap = new LinkedHashMap<>();
        myMap.put("hello", "world");

        assertThat(myMap, hasEntry("hello", "world"));
        assertThat(myMap, hasKey("hello"));
        assertThat(myMap, hasValue("world"));
    }

    @Test
    public void string_matcher_examples() throws Exception {
        assertThat("hello world", startsWith("hello"));
        assertThat("hello world", endsWith("world"));
        assertThat("hello world", containsString("ell"));
        assertThat("hello world", equalToIgnoringCase("HELLO WORLD"));
    }
}
