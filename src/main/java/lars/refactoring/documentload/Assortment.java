package lars.refactoring.documentload;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

class Assortment {

    private List<Album> albums;

    public List<Album> getAlbums() {
        return Collections.unmodifiableList(albums);
    }

    static Assortment fromJson(String json)  {
        try {
            return Json.mapper().readValue(json, Assortment.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    String toJson() {
        try {
            return Json.mapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
