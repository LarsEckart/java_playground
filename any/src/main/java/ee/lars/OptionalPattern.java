package ee.lars;

import java.util.Optional;

public interface OptionalPattern<T> {

    public abstract Optional<T> findValue(long id);

    public default boolean containsValue(long id) {
        return findValue(id).isPresent();
    }

    public default T getValue(long id) {
        return findValue(id).orElseThrow(() -> new RuntimeException("boom"));
    }

}