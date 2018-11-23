package lars.katas.gildedrose;

class GildedRose {

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            ItemCategory itemCategory = categorize(item);
            itemCategory.updateQuality();
            itemCategory.updateSellIn();
        }
    }

    private ItemCategory categorize(Item item) {
        if (item.name.equals("foo")) {
            return new NormalItem(item);
        }
        if (item.name.equals("Aged Brie")) {
            return new Cheese(item);
        }
        if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            return new Backstage(item);
        }
        if (item.name.equals("Sulfuras, Hand of Ragnaros")) {
            return new Legendary();
        }
        throw new RuntimeException("unknown item");
    }

    private interface ItemCategory {

        public void updateQuality();

        void updateSellIn();
    }

    private class NormalItem implements ItemCategory {

        private Item item;

        public NormalItem(Item item) {
            this.item = item;
        }

        @Override
        public void updateQuality() {
            if (item.quality > 0) {
                if (item.sellIn <= 0) {
                    item.quality = item.quality - 2 < 0 ? 0 : item.quality - 2;
                } else {
                    item.quality = item.quality - 1;
                }
            }
        }

        @Override
        public void updateSellIn() {
            item.sellIn--;
        }
    }

    private class Cheese implements ItemCategory {

        private Item item;

        public Cheese(Item item) {
            this.item = item;
        }

        @Override
        public void updateQuality() {
            if (item.quality < 50) {
                if (item.sellIn <= 0) {
                    item.quality = item.quality + 2 > 50 ? 50 : item.quality + 2;
                } else {
                    item.quality = item.quality + 1;
                }
            }
        }

        @Override
        public void updateSellIn() {
            item.sellIn--;
        }
    }

    private class Backstage implements ItemCategory {

        private Item item;

        public Backstage(Item item) {
            this.item = item;
        }

        @Override
        public void updateQuality() {
            if (item.sellIn <= 0) {
                item.quality = 0;
            } else if (item.quality < 50) {
                if (item.sellIn <= 5) {
                    item.quality = item.quality + 3 > 50 ? 50 : item.quality + 3;
                } else if (item.sellIn <= 10) {
                    item.quality = item.quality + 2 > 50 ? 50 : item.quality + 2;
                } else {
                    item.quality = item.quality + 1;
                }
            }
        }

        @Override
        public void updateSellIn() {
            item.sellIn--;
        }
    }

    private class Legendary implements ItemCategory {

        @Override
        public void updateQuality() {

        }

        @Override
        public void updateSellIn() {
        }
    }

}
