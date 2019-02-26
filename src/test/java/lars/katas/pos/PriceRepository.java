package lars.katas.pos;

import java.util.Optional;

public interface PriceRepository {

    Optional<Long> getFor(String item);
}
