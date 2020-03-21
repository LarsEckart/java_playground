package lars.functional;

import java.util.Optional;

/** http://blog.joda.org/2015/09/naming-optional-query-methods.html */
public interface OptionalPattern<T> {

  /** return empty optional if no attribute */
  public abstract Optional<T> findValue(long id);

  public default boolean containsValue(long id) {
    return findValue(id).isPresent();
  }

  /** throw exception if no attribute */
  public default T getValue(long id) {
    return findValue(id).orElseThrow(() -> new RuntimeException("boom"));
  }
}
