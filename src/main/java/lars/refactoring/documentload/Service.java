package lars.refactoring.documentload;

import java.io.IOException;

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

    private Assortment loadAssortment(String query)  {
        try {
            return Json.mapper().readValue(dataSource.getAlbumList(query), Assortment.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
