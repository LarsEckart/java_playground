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

        if (!item.name.equals(AGED_BRIE) && !item.name.equals(BACKSTAGE_PASSES)) {
            if (item.quality > 0) {
                item.quality = item.quality - 1;
            }
        } else {
            if (item.quality < MAX_QUALITY) {
                item.quality = item.quality + 1;

                if (item.name.equals(BACKSTAGE_PASSES)) {
                    if (item.sellIn < 11) {
                        if (item.quality < MAX_QUALITY) {
                            increaseQualityByOne(item);
                        }
                    }

                    if (item.sellIn < 6) {
                        if (item.quality < MAX_QUALITY) {
                            increaseQualityByOne(item);
                        }
                    }
                }
            }
        }

        if (item.sellIn < 0) {
            if (item.name.equals(AGED_BRIE)) {
                if (item.quality < MAX_QUALITY) {
                    item.quality = item.quality + 1;
                }
            } else {
                if (item.name.equals(BACKSTAGE_PASSES)) {
                    item.quality = 0;
                } else {
                    if (item.quality > 0) {
                        item.quality = item.quality - 1;
                    }
                }
            }
        }
    }

    private void decreaseQualityByOne(Item item) {
        item.quality = item.quality - 1;
    }

    private void increaseQualityByOne(Item item) {
        item.quality = item.quality + 1;
    }
}
