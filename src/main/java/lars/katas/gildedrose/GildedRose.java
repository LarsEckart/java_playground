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
            item.sellIn = item.sellIn - 1;

            if (isItemThatDecreasesInQuality(item)) {
                if (item.quality > 0) {
                    item.quality = item.quality - 1;
                }
            } else {
                if (item.quality < MAX_QUALITY) {
                    item.quality = item.quality + 1;

                    if (isBackstagePass(item)) {
                        additionalQualityIncreaseForBackstagePass(item);
                    }
                }
            }

            if (item.sellIn < 0) {
                updateQualityOncePastExpiry(item);
            }
        }
    }

    private void updateQualityOncePastExpiry(Item item) {
        if (!item.name.equals(AGED_BRIE)) {
            if (isBackstagePass(item)) {
                item.quality = 0;
            } else {
                if (item.quality > 0) {
                    item.quality = item.quality - 1;
                }
            }
        } else {
            if (item.quality < MAX_QUALITY) {
                item.quality = item.quality + 1;
            }
        }
    }

    private void additionalQualityIncreaseForBackstagePass(Item item) {
        if (item.sellIn < 11) {
            if (item.quality < MAX_QUALITY) {
                item.quality = item.quality + 1;
            }
        }

        if (item.sellIn < 6) {
            if (item.quality < MAX_QUALITY) {
                item.quality = item.quality + 1;
            }
        }
    }

    private boolean isBackstagePass(Item item) {
        return item.name.equals(BACKSTAGE_PASSES);
    }

    private boolean isItemThatDecreasesInQuality(Item item) {
        return !item.name.equals(AGED_BRIE) && !isBackstagePass(item);
    }
}
