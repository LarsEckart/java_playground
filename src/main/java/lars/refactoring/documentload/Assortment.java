package lars.refactoring.documentload;

import java.util.Collections;
import java.util.List;

class Assortment {

  private List<Album> albums;

  public List<Album> getAlbums() {
    return Collections.unmodifiableList(albums);
  }
}
