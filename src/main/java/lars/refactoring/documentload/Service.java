package lars.refactoring.documentload;

import java.io.IOException;

class Service {

    private DataSource dataSource;

    public String tuesdayMusic(String query) {
        try {
            Assortment data = loadAssortment(dataSource.getAlbumList(query));
            return Json.mapper().writeValueAsString(data);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    private static Assortment loadAssortment(String json)  {
        try {
            return Json.mapper().readValue(json, Assortment.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
