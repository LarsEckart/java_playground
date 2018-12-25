package lars.refactoring.documentload;

class Service {

    private DataSource dataSource;

    Service(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String tuesdayMusic(String query) {
        try {
            Assortment data = Assortment.fromJson(dataSource.getAlbumList(query));
            return data.toJson();
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}
