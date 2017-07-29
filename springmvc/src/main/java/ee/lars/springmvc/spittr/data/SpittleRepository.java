package ee.lars.springmvc.spittr.data;

import ee.lars.springmvc.spittr.Spittle;

import java.util.List;

public interface SpittleRepository {

    List<Spittle> findSpittles(long max, int count);

}
