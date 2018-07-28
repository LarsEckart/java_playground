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
            + "Item{name='+5 Dexterity Vest', sellIn=10, quality=20}\n"
            + "Item{name='Aged Brie', sellIn=2, quality=0}\n"
            + "Item{name='Elixir of Mongoose', sellIn=5, quality=7}\n"
            + "Item{name='Sulfuras, Hand of Ragnaros', sellIn=0, quality=80}\n"
            + "Item{name='Sulfuras, Hand of Ragnaros', sellIn=-1, quality=80}\n"
            + "Item{name='Backstage passes to a TAFKAL80ETC concert', sellIn=15, quality=20}\n"
            + "Item{name='Backstage passes to a TAFKAL80ETC concert', sellIn=10, quality=49}\n"
            + "Item{name='Backstage passes to a TAFKAL80ETC concert', sellIn=5, quality=49}\n"
            + "\n"
            + "--- day 1 ---\n"
            + "name   sellin   quality\n"
            + "Item{name='+5 Dexterity Vest', sellIn=9, quality=19}\n"
            + "Item{name='Aged Brie', sellIn=1, quality=1}\n"
            + "Item{name='Elixir of Mongoose', sellIn=4, quality=6}\n"
            + "Item{name='Sulfuras, Hand of Ragnaros', sellIn=0, quality=80}\n"
            + "Item{name='Sulfuras, Hand of Ragnaros', sellIn=-1, quality=80}\n"
            + "Item{name='Backstage passes to a TAFKAL80ETC concert', sellIn=14, quality=21}\n"
            + "Item{name='Backstage passes to a TAFKAL80ETC concert', sellIn=9, quality=50}\n"
            + "Item{name='Backstage passes to a TAFKAL80ETC concert', sellIn=4, quality=50}\n"
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
                out.println(item);
            }
            out.println();
            gildedRose.updateQuality();
        }

        assertThat(output.toString()).isEqualTo(expected);
    }
}
