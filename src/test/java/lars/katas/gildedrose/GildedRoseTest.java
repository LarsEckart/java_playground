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

    @Test
    public void the_Quality_of_an_item_is_never_negative() throws Exception {
        givenItemWithSellinAndQuality("foo", 10, 0);
        whenWeUpdateTheQuality();
        thenItemBecomes("foo", 9, 0);
    }

    @Test
    public void once_the_sell_by_date_has_passed_Quality_degrades_twice_as_fast() throws Exception {
        givenItemWithSellinAndQuality("foo", 0, 10);
        whenWeUpdateTheQuality();
        thenItemBecomes("foo", -1, 8);
    }

    @Test
    public void once_the_sell_by_date_has_passed_Quality_degrades_twice_as_fast_2() throws Exception {
        givenItemWithSellinAndQuality("foo", 0, 1);
        whenWeUpdateTheQuality();
        thenItemBecomes("foo", -1, 0);
    }

    @Test
    public void Aged_Brie__actually_increases_in_Quality_the_older_it_gets() throws Exception {
        givenItemWithSellinAndQuality("Aged Brie", 10, 10);
        whenWeUpdateTheQuality();
        thenItemBecomes("Aged Brie", 9, 11);
    }

    @Test
    public void the_Quality_of_an_item_is_never_more_than_50() throws Exception {
        givenItemWithSellinAndQuality("Aged Brie", 10, 50);
        whenWeUpdateTheQuality();
        thenItemBecomes("Aged Brie", 9, 50);
    }

    @Test
    public void Sulfuras_being_a_legendary_item_never_has_to_be_sold_or_decreases_in_Quality() throws Exception {
        String sulfuras = "Sulfuras, Hand of Ragnaros";
        givenItemWithSellinAndQuality(sulfuras, 10, 80);
        whenWeUpdateTheQuality();
        thenItemBecomes(sulfuras, 10, 80);
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
