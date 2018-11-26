package lars.katas.gildedrose;

class GildedRose {

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            ItemCategory itemCategory = ItemCategoryFactory.of(item);
            itemCategory.updateQuality();
        }
    }
}
