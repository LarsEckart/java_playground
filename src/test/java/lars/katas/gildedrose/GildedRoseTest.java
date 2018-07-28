package lars.katas.gildedrose;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GildedRoseTest {

    final String expected = "--- day 0 ---\n"
            + "name   sellin   quality\n"
            + "+5 Dexterity Vest   10   20\n"
            + "Aged Brie   2   0\n"
            + "Elixir of Mongoose   5   7\n"
            + "Sulfuras, Hand of Ragnaros   0   80\n"
            + "Sulfuras, Hand of Ragnaros   -1   80\n"
            + "Backstage passes to a TAFKAL80ETC concert   15   20\n"
            + "Backstage passes to a TAFKAL80ETC concert   10   49\n"
            + "Backstage passes to a TAFKAL80ETC concert   5   49\n"
            + "\n"
            + "--- day 1 ---\n"
            + "name   sellin   quality\n"
            + "+5 Dexterity Vest   9   19\n"
            + "Aged Brie   1   1\n"
            + "Elixir of Mongoose   4   6\n"
            + "Sulfuras, Hand of Ragnaros   0   80\n"
            + "Sulfuras, Hand of Ragnaros   -1   80\n"
            + "Backstage passes to a TAFKAL80ETC concert   14   21\n"
            + "Backstage passes to a TAFKAL80ETC concert   9   50\n"
            + "Backstage passes to a TAFKAL80ETC concert   4   50\n"
            + "\n";

    @Test
    public void gilded() throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(output);

        Item[] items = new Item[] {
                new Item("+5 Dexterity Vest", 10, 20),
                new Item("Aged Brie", 2, 0),
                new Item("Elixir of Mongoose", 5, 7),
                new Item("Sulfuras, Hand of Ragnaros", 0, 80),
                new Item("Sulfuras, Hand of Ragnaros", -1, 80),
                new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
        };

        GildedRose gildedRose = new GildedRose(items);

        int days = 2;

        for (int i = 0; i < days; i++) {
            out.println("--- day " + i + " ---");
            out.println("name   sellin   quality");
            for (Item item : items) {
                out.println(item.name + "   " + item.sellIn + "   " + item.quality);
            }
            out.println();
            gildedRose.updateQuality();
        }

        System.out.println(output.toString());
        assertThat(output.toString()).isEqualTo(expected);
    }
}
