package lars.refactoring.documentload;

class Service {

    private DataSource dataSource;

    public String tuesdayMusic(String query) {
        try {
            Assortment data = loadAssortment(query);
            return Json.mapper().writeValueAsString(data);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    private Assortment loadAssortment(String query) throws java.io.IOException {
        return Json.mapper().readValue(dataSource.getAlbumList(query), Assortment.class);
    }
}
