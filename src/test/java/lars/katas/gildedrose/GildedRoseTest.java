package lars.katas.gildedrose;

import org.approvaltests.combinations.CombinationApprovals;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GildedRoseTest {

    @Test
    public void foo() throws Exception {

        CombinationApprovals.verifyAllCombinations(this::doUpdateQuality,
                new String[] {"foo", "Aged Brie", "Backstage passes to a TAFKAL80ETC concert", "Sulfuras, Hand of Ragnaros"},
                new Integer[] {-1, 0, 2, 8, 9, 10, 11},
                new Integer[] {0, 1, 2, 48, 49, 50});
    }

    private String doUpdateQuality(String name, int sellIn, int quality) {
        Item[] items = new Item[] {new Item(name, sellIn, quality)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        return app.items[0].toString();
    }

    private GildedRose app;

    @Test
    public void at_the_end_of_each_day_our_system_lowers_both_values_for_every_item() {
        givenItemWithSellinAndQuality("foo", 10, 10);
        whenWeUpdateTheQuality();
        thenItemBecomes("foo", 9, 9);
    }

    private void givenItemWithSellinAndQuality(String name, int sellIn, int quality) {
        Item[] items = new Item[] {new Item(name, sellIn, quality)};
        app = new GildedRose(items);
    }

    private void whenWeUpdateTheQuality() {
        app.updateQuality();
    }

    private void thenItemBecomes(String name, int sellIn, int quality) {
        assertThat(app.items[0].name).isEqualTo(name);
        assertThat(app.items[0].sellIn).isEqualTo(sellIn);
        assertThat(app.items[0].quality).isEqualTo(quality);
    }
}
