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
        if (isItemThatDecreasesInQuality(item)) {
            if (item.quality > 0) {
                decreaseQualityByOne(item);

            }
        } else {
            if (item.quality < MAX_QUALITY) {
                increaseQualityByOne(item);

                if (isBackstagePass(item)) {
                    additionalQualityIncreaseForBackstagePass(item);
                }
            }
        }

        if (item.sellIn < 0) {
            updateQualityOncePastExpiry(item);
        }
    }

    private boolean isItemThatDecreasesInQuality(Item item) {
        return !item.name.equals(AGED_BRIE) && !isBackstagePass(item);
    }

    private void decreaseQualityByOne(Item item) {
        item.quality = item.quality - 1;
    }

    private void increaseQualityByOne(Item item) {
        item.quality = item.quality + 1;
    }

    private boolean isBackstagePass(Item item) {
        return item.name.equals(BACKSTAGE_PASSES);
    }

    private void updateQualityOncePastExpiry(Item item) {
        if (item.name.equals(AGED_BRIE)) {
            if (item.quality < MAX_QUALITY) {
                increaseQualityByOne(item);
            }
        } else {
            if (isBackstagePass(item)) {
                item.quality = 0;
            } else {
                if (item.quality > 0) {
                    decreaseQualityByOne(item);
                }
            }
        }
    }

    private void additionalQualityIncreaseForBackstagePass(Item item) {
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
