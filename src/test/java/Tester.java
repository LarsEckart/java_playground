
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Tester {

    private List<String> list = new ArrayList<>();

    @Test
    public void testFirst() {
        list.add("one");
        assertEquals(1, list.size());
    }

    @Test
    public void testSecond() {
        assertEquals(0, list.size());
    }
}
