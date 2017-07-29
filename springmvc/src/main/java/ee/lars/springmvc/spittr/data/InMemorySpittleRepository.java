package ee.lars.springmvc.spittr.data;

import ee.lars.springmvc.spittr.Spittle;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemorySpittleRepository implements SpittleRepository {

    private List<Spittle> spittles = new ArrayList<>();

    @Override
    public List<Spittle> findSpittles(long max, int count) {
        return this.spittles;
    }

    @Override
    public Spittle findOne(long id) {
        return this.spittles.get((int) id);
    }
}
