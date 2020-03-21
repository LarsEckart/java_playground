package lars.refactoring.documentload;

import java.util.Collections;
import java.util.List;

class Album {

  private String artist;
  private String title;
  private List<Track> tracks;

  public String getArtist() {
    return artist;
  }

  public String getTitle() {
    return title;
  }

  public List<Track> getTracks() {
    return Collections.unmodifiableList(tracks);
  }
}
