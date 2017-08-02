package ee.lars.springmvc.spittr.data;

import ee.lars.springmvc.spittr.Spittle;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class InMemorySpittleRepository implements SpittleRepository {

    private List<Spittle> spittles = new ArrayList<>();

    @Override
    public List<Spittle> findSpittles(long max, int count) {
        this.spittles.add(new Spittle("hello world", LocalDate.now()));
        this.spittles.add(new Spittle("1 2 3", LocalDate.now()));
        this.spittles.add(new Spittle("immer wenn es regnet...", LocalDate.now()));
        return this.spittles;
    }

    @Override
    public Spittle findOne(long id) {
        return this.spittles.get((int) id);
    }

    @Override
    public Spittle save(Spittle spittle) {
        return null;
    }
}
