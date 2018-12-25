package lars.refactoring.documentload;

class Service {

    private DataSource dataSource;

    public String tuesdayMusic(String query) {
        try {
            Assortment data = Assortment.fromJson(dataSource.getAlbumList(query));
            return Json.mapper().writeValueAsString(data);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}
