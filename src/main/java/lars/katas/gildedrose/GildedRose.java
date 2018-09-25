package lars.katas.gildedrose;

class GildedRose {

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            ItemCategory category = categorize(item);
            category.updateOneItem(item, this);
        }
    }

    private ItemCategory categorize(Item item) {
if (item.name.equals("Sulfuras, Hand of Ragnaros")) {
    return new Legendary();
}
        if (item.name.equals("Aged Brie")) {
            return new Cheese();
        }
        if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            return new BackstagePass();
        }

        if (item.name.startsWith("Conjured")) {
            return new Conjured();
        }
        return new ItemCategory();
    }

    private boolean hasExpired(Item item) {
        return item.sellIn < 0;
    }

    private class ItemCategory {

        protected void incrementQuality(Item item) {
            if (item.quality < 50) {
                item.quality = item.quality + 1;
            }
        }

        protected void decrementQuality(Item item) {
            if (item.quality > 0) {
                item.quality = item.quality - 1;
            }
        }

        private void updateOneItem(Item item, GildedRose gildedRose) {
            updateQuality(item);

            updateSellIn(item);

            if (gildedRose.hasExpired(item)) {
                updateExpired(item);
            }
        }

        protected void updateQuality(Item item) {
            decrementQuality(item);
        }

        protected void updateSellIn(Item item) {
            item.sellIn = item.sellIn - 1;
        }

        protected void updateExpired(Item item) {
            decrementQuality(item);
        }
    }

    private class Legendary extends ItemCategory {

        @Override
        protected void updateQuality(Item item) {

        }

        @Override
        protected void updateSellIn(Item item) {

        }

        @Override
        protected void updateExpired(Item item) {
        }
    }

    private class Cheese extends ItemCategory {

        @Override
        protected void updateQuality(Item item) {
            incrementQuality(item);
        }

        @Override
        protected void updateExpired(Item item) {
            incrementQuality(item);
        }
    }

    private class BackstagePass extends ItemCategory {

        @Override
        protected void updateQuality(Item item) {
            incrementQuality(item);

            if (item.sellIn < 11) {
                incrementQuality(item);
            }

            if (item.sellIn < 6) {
                incrementQuality(item);
            }
        }

        @Override
        protected void updateExpired(Item item) {
            item.quality = 0;
        }
    }

    private class Conjured extends ItemCategory {

        @Override
        protected void updateQuality(Item item) {
            decrementQuality(item);
            decrementQuality(item);
        }

        @Override
        protected void updateExpired(Item item) {
            decrementQuality(item);
            decrementQuality(item);
        }
    }
}
