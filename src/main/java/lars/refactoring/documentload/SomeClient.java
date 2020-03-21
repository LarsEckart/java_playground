package lars.refactoring.documentload;

import java.util.List;
import java.util.stream.Collectors;

class SomeClient {

  public List<String> doSomething(Assortment anAssortment) {
    List<String> titles =
        anAssortment.getAlbums().stream().map(Album::getTitle).collect(Collectors.toList());
    return somethingCleverWith(titles);
  }

  private List<String> somethingCleverWith(List<String> titles) {
    return titles.stream().map(String::toUpperCase).collect(Collectors.toList());
  }
}
