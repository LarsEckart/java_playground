package lars.katas.gildedrose;

class GildedRose {

    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final int MAX_QUALITY = 50;

    Item[] items;

    GildedRose(Item[] items) {
        this.items = items;
    }

    void updateQuality() {
        for (Item item : items) {
            if (SULFURAS.equals(item.name)) {
                continue;
            }
            decreaseSellIn(item);

            adjustQuality(item);
        }
    }

    private void decreaseSellIn(Item item) {
        item.sellIn = item.sellIn - 1;
    }

    private void adjustQuality(Item item) {

        if ("foo".equals(item.name)) {
            if (item.quality != 0) {
                if (item.sellIn > 0) {
                    item.quality = item.quality - 1;
                }
                if (item.sellIn <= 0) {
                    item.quality = item.quality - 2;
                }
            }
            return;
        }

        if (AGED_BRIE.equals(item.name)) {
            if (item.quality < MAX_QUALITY) {
                item.quality = item.quality + 1;
            }
            return;
        }

        if (item.name.equals(BACKSTAGE_PASSES)) {
            if (item.sellIn < 5) {
                if (item.quality < 48) {
                    item.quality = item.quality + 3;
                } else {
                    item.quality = MAX_QUALITY;
                }
            } else if (item.sellIn < 10) {
                if (item.quality < 49) {
                    item.quality = item.quality + 2;
                } else {
                    item.quality = MAX_QUALITY;
                }
            }
            if (item.sellIn < 0) {
                item.quality = 0;
            }
            return;
        }
        if (SULFURAS.equals(item.name)) {
            return;
        }
    }
}
