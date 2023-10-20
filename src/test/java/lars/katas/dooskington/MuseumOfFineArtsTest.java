package lars.katas.dooskington;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lars.katas.dooskington.MuseumOfFineArts.Museum;
import lars.katas.dooskington.MuseumOfFineArts.Painting;
import org.junit.jupiter.api.Test;

class MuseumOfFineArtsTest {

  @Test
  public void MuseumWithOneAnonymousPainting_GetCountOfAnonymousPaintings_ReturnsOne() {
    Museum m = new Museum();
    Painting p = new Painting();
    p.name = "Painting with unknown artist";

    m.Paintings.add(p);
    Painting pp = new Painting();
    pp.name = "Painting with known artist";
    pp.artist = "Deadbeef";
    m.Paintings.add(pp);

    long count = m.GetCountOfAnonymousPaintings();

    assertEquals(1, count);
  }

  @Test
  public void WorthlessPainting_FinalPaintingValue_ReturnsZero() {
    Museum m = new Museum();
    Painting p = new Painting();
    p.value = 0.0;

    double actual = m.FinalPaintingValue(p);

    assertEquals(0.0, actual);
  }

  @Test
  public void RarePaintingWithNonTrendingArtMarket_FinalPaintingValue_IsCorrect() {
    Museum m = new Museum();
    m.IsArtCurrentlyTrending = false;
    Painting p = new Painting();
    p.value = 2.0;
    p.rarity = "rare";

    double actual = m.FinalPaintingValue(p);

    assertEquals(4.8, actual, 0.00001);
  }

  @Test
  public void
      UniquePaintingWithNonTrendingArtMarketAndPopularRarity_FinalPaintingValue_IsCorrect() {
    Museum m = new Museum();
    m.IsArtCurrentlyTrending = false;
    m.CurrentlyPopularRarity = "unique";
    Painting p = new Painting();
    p.value = 4.0;
    p.rarity = "unique";

    double actual = m.FinalPaintingValue(p);

    assertEquals(9.6, actual, 0.00001);
  }
}
