package ee.lars.springmvc.spittr.data;

import ee.lars.springmvc.spittr.Spitter;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

public interface SpitterRepository {

    @CachePut("userCache")
    Spitter save(Spitter unsaved);

    @Cacheable("userCache")
    Spitter findByUsername(String username);
}
