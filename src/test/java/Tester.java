import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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

        int[] arr = {42};

        if (arr[0] == 1) {

        }

        arr[0] = 1;
        System.out.println(arr[0]);

        File icon = tempFolder.newFile("icon.png");
        File assets = tempFolder.newFolder("assets");
        createAssets(assets, 3);

        //DigitalAssetManager dam = new DigitalAssetManager(icon, assets);
        //assertEquals(3, dam.getAssetCount());
    }

    private void createAssets(File assets, int numberOfAssets) throws IOException {
        for (int index = 0; index < numberOfAssets; index++) {
            File asset = new File(assets, String.format("asset-%d.mpg", index));
            Assert.assertTrue("Asset couldn't be created.", asset.createNewFile());
        }
    }
}
