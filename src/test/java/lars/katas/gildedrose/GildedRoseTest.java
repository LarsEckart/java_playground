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

    @Test
    public void once_sellin_is_zero_then_quality_degrades_twice_as_fast() throws Exception {
        givenItemWithSellinAndQuality("foo", 0, 4);

        whenWeUpdateTheQuality();

        thenItemBecomes("foo", -1, 2);
    }

    @Test
    public void aged_brie_increases_quality_the_older_it_gets() throws Exception {
        givenItemWithSellinAndQuality("Aged Brie", 3, 3);

        whenWeUpdateTheQuality();

        thenItemBecomes("Aged Brie", 2, 4);
    }

    @Test
    public void quality_of_an_item_is_never_negative() throws Exception {
        givenItemWithSellinAndQuality("foo", 1, 0);

        whenWeUpdateTheQuality();

        thenItemBecomes("foo", 0, 0);
    }

    @Test
    public void quality_of_an_item_is_never_above_50() throws Exception {
        givenItemWithSellinAndQuality("Aged Brie", 3, 50);

        whenWeUpdateTheQuality();

        thenItemBecomes("Aged Brie", 2, 50);
    }

    @Test
    public void sulfras_never_has_to_be_sold_or_decreases_in_quality() throws Exception {
        givenItemWithSellinAndQuality("Sulfuras, Hand of Ragnaros", 3, 50);

        whenWeUpdateTheQuality();

        thenItemBecomes("Sulfuras, Hand of Ragnaros", 3, 50);
    }

    @Test
    public void backstage_passes_increase_by_2_when_less_10_sellin_days() throws Exception {
        givenItemWithSellinAndQuality("Backstage passes to a TAFKAL80ETC concert", 10, 20);

        whenWeUpdateTheQuality();

        thenItemBecomes("Backstage passes to a TAFKAL80ETC concert", 9, 22);
    }

    @Test
    public void backstage_passes_increase_by_2_when_less_10_sellin_days2() throws Exception {
        givenItemWithSellinAndQuality("Backstage passes to a TAFKAL80ETC concert", 10, 49);

        whenWeUpdateTheQuality();

        thenItemBecomes("Backstage passes to a TAFKAL80ETC concert", 9, 50);
    }

    @Test
    public void backstage_passes_increase_by_3_when_less_5_sellin_days() throws Exception {
        givenItemWithSellinAndQuality("Backstage passes to a TAFKAL80ETC concert", 5, 20);

        whenWeUpdateTheQuality();

        thenItemBecomes("Backstage passes to a TAFKAL80ETC concert", 4, 23);
    }

    @Test
    public void backstage_passes_increase_by_3_when_less_5_sellin_days3() throws Exception {
        givenItemWithSellinAndQuality("Backstage passes to a TAFKAL80ETC concert", 5, 49);

        whenWeUpdateTheQuality();

        thenItemBecomes("Backstage passes to a TAFKAL80ETC concert", 4, 50);
    }

    @Test
    public void backstage_passes_quality_drops_to_0_after_event() throws Exception {
        givenItemWithSellinAndQuality("Backstage passes to a TAFKAL80ETC concert", 0, 20);

        whenWeUpdateTheQuality();

        thenItemBecomes("Backstage passes to a TAFKAL80ETC concert", -1, 0);
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
