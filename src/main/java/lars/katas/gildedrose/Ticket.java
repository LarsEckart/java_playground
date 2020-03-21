package lars.katas.gildedrose;

class Ticket implements ItemCategory {

  private final Item item;

  public Ticket(Item item) {
    this.item = item;
  }

  @Override
  public void updateQuality() {
    if (item.quality < 50) {
      item.quality = item.quality + 1;

      if (item.sellIn < 11) {
        if (item.quality < 50) {
          item.quality = item.quality + 1;
        }
      }

      if (item.sellIn < 6) {
        if (item.quality < 50) {
          item.quality = item.quality + 1;
        }
      }
    }

    item.sellIn = item.sellIn - 1;

    if (item.sellIn < 0) {
      item.quality = 0;
    }
  }
}
