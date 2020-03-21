package lars.katas.gildedrose;

class Normal implements ItemCategory {

  private final Item item;

  public Normal(Item item) {
    this.item = item;
  }

  @Override
  public void updateQuality() {
    if (item.quality > 0) {
      item.quality = item.quality - 1;
    }

    item.sellIn = item.sellIn - 1;

    if (item.sellIn < 0) {
      if (item.quality > 0) {
        item.quality = item.quality - 1;
      }
    }
  }
}
