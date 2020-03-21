package lars.refactoring.documentload;

class Service {

  private DataSource dataSource;

  Service(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public String tuesdayMusic(String query) {
    try {
      Assortment data = Json.mapper().readValue(dataSource.getAlbumList(query), Assortment.class);
      return Json.mapper().writeValueAsString(data);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
