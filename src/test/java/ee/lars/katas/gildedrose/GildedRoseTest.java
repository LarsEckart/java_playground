package ee.lars.katas.gildedrose;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GildedRoseTest {

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

    @Test
    public void backstage_passes_increases_in_Quality_as_its_SellIn_value_approaches() throws Exception {
        backstagePassQuality(20, 10, 11);
        backstagePassQuality(11, 10, 11);
        backstagePassQuality(10, 10, 12);
        backstagePassQuality(9, 10, 12);
        backstagePassQuality(6, 10, 12);
        backstagePassQuality(5, 10, 13);
        backstagePassQuality(4, 10, 13);
        backstagePassQuality(1, 10, 13);
        backstagePassQuality(0, 10, 0);
        backstagePassQuality(-1, 10, 0);

        backstagePassQuality(20, 50, 50);
        backstagePassQuality(9, 50, 50);
        backstagePassQuality(3, 50, 50);
    }

    private void backstagePassQuality(int sellIn, int startQuality, int expectedQuality) {
        String backstagePass = "Backstage passes to a TAFKAL80ETC concert";
        givenItemWithSellinAndQuality(backstagePass, sellIn, startQuality);
        whenWeUpdateTheQuality();
        assertEquals("quality for sellIn " + sellIn, expectedQuality, app.items[0].quality);
        assertEquals("new sellIn for old sellIn " + sellIn, sellIn - 1, app.items[0].sellIn);
    }

    private void whenWeUpdateTheQuality() {
        app.updateQuality();
    }

    private void givenItemWithSellinAndQuality(String name, int sellIn, int quality) {
        Item[] items = new Item[]{new Item(name, sellIn, quality)};
        app = new GildedRose(items);
    }

    private void thenItemBecomes(String name, int sellIn, int quality) {
        assertEquals(name, app.items[0].name);
        assertEquals("sellIn", sellIn, app.items[0].sellIn);
        assertEquals("quality", quality, app.items[0].quality);
    }
}