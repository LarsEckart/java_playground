package lars.katas.gildedrose;

class ItemCategoryFactory {

    public static ItemCategory of(Item item) {
        switch (item.name) {
            case "Aged Brie":
                return new Cheese(item);
            case "Backstage passes to a TAFKAL80ETC concert":
                return new Ticket(item);
            case "Sulfuras, Hand of Ragnaros":
                return new Legendary();
            default:
                return new Normal(item);
        }
    }
}
