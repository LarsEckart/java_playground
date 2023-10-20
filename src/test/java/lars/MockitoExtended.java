package lars;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.function.Function;

public class MockitoExtended {

  public static <T> T stub(Class<T> klass, Function<T, Object> when, Object returnValue) {
    try {
      T result = mock(klass);
      when(when.apply(result)).thenReturn(returnValue);
      return result;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
