import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class Tester {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
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

    @Test
    public void countsAssets() throws IOException {

        File icon = tempFolder.newFile("icon.png");
        File assets = tempFolder.newFolder("assets");
        createAssets(assets, 3);
    }

    private void createAssets(File assets, int numberOfAssets) throws IOException {
        for (int index = 0; index < numberOfAssets; index++) {
            File asset = new File(assets, String.format("asset-%d.mpg", index));
            Assert.assertTrue("Asset couldn't be created.", asset.createNewFile());
        }
    }

    @Test
    public void should_tostring_a_list_with_one_element() throws Exception {
        // given
        List<String> anyList = Collections.singletonList("hello world");

        // when
        final String toString = anyList.toString();

        // then
        assertThat(toString).isEqualTo("[hello world]");
    }

    @Test
    public void should_tostring_a_list_with_multiple_elements() throws Exception {
        // given
        List<String> anyList = Arrays.asList("hello world", "more than one", "i have a , comma");

        // when
        final String toString = String.valueOf(anyList);

        // then
        assertThat(toString).isEqualTo("[hello world, more than one, i have a , comma]");
    }
}
