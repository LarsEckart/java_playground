package lars.katas.gildedrose;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GildedRoseTest {

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
