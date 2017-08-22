package ee.lars.testing;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

public class JUnitTest {

    @Test
    public void assertEquals_example() throws Exception {
        assertEquals("hello", "hello");

        // assertEquals("expected", "actual");
        // org.junit.ComparisonFailure:
        // Expected :expected
        // Actual   :actual
    }

    @Test
    public void assertNotEquals_example() throws Exception {
        assertNotEquals("hello", "world");

        // assertNotEquals("hello", "hello");
        // AssertionError: Values should be different.
    }

    @Test
    public void assertArrayEquals_example() throws Exception {
        final int[] anyArray = {1, 2, 3};
        final int[] anyOtherArray = {1, 5, 6};
        assertArrayEquals(anyArray, anyArray);

        // assertArrayEquals(anyArray, anyOtherArray);
        // arrays first differed at element [1];
        // Expected :2
        // Actual   :5
    }

    @Test
    public void assertNotNull_example() throws Exception {
        final String text = "hello world";
        assertNotNull(text);

        // assertNotNull(null);
        // AssertionError
    }

    @Test
    public void assertNull_example() throws Exception {
        final String text = null;
        assertNull(text);

        // assertNull("hello world");
        // AssertionError: expected null, but was:<hello world>
    }

    @Test
    public void assertSame_example() throws Exception {
        Person o1 = new Person("Oskar", 1);
        Person o2 = o1;
        assertSame(o1, o2);
        assertEquals(o1, o2);

        // Person o3 = new Person("Oskar", 1);
        // assertSame(o1, o3);
        // AssertionError: expected same:<ee.lars.testing.JUnitTest$Person@3581c5f3> was not:<ee.lars.testing.JUnitTest$Person@6aa8ceb6>
    }

    class Person {

        public String name;
        public int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    @Test
    public void assertNotSame_example() throws Exception {
        Person o1 = new Person("Oskar", 1);
        Person o2 = new Person("Oskar", 1);
        assertNotSame(o1, o2);

        // Person o3 = o1;
        // assertNotSame(o1, o3);
        // AssertionError: expected not same
    }

    @Test
    public void assertThat_example() throws Exception {
        assertThat(42, is(42));

        // assertThat(42, is(7));
        // AssertionError:
        // Expected: is <7>
        //        but: was <42>
    }
}
