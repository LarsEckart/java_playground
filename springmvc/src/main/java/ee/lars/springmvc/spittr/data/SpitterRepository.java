package ee.lars.springmvc.spittr.data;

import ee.lars.springmvc.spittr.Spitter;

public interface SpitterRepository {

    Spitter save(Spitter unsaved);

    Spitter findByUsername(String username);
}
