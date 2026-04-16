package lars;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class ReadOnceTest {

  @Test
  void returns_the_value_on_first_read() {
    ReadOnce readOnce = new ReadOnce("secret");

    String unwrapped = readOnce.unwrap();

    assertThat(unwrapped).isEqualTo("secret");
  }

  @Test
  void throws_on_second_read() {
    ReadOnce readOnce = new ReadOnce("secret");
    readOnce.unwrap();

    assertThatThrownBy(readOnce::unwrap)
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("already read");
  }

  @Test
  void redacts_value_when_rendered_as_string() {
    ReadOnce readOnce = new ReadOnce("secret");

    String rendered = readOnce.toString();

    assertThat(rendered).isEqualTo("[redacted]");
  }

  @Test
  void allows_only_one_successful_read_across_threads() throws Exception {
    ReadOnce readOnce = new ReadOnce("secret");
    CountDownLatch allWorkersReady = new CountDownLatch(2);
    CountDownLatch startSignal = new CountDownLatch(1);

    Callable<Object> readTask =
        () -> {
          allWorkersReady.countDown();
          startSignal.await();

          try {
            return readOnce.unwrap();
          } catch (Exception exception) {
            return exception;
          }
        };

    try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
      Future<Object> resultOne = executorService.submit(readTask);
      Future<Object> resultTwo = executorService.submit(readTask);

      boolean workersAreReady = allWorkersReady.await(1, TimeUnit.SECONDS);
      startSignal.countDown();

      Object outcomeOne = resultOne.get(1, TimeUnit.SECONDS);
      Object outcomeTwo = resultTwo.get(1, TimeUnit.SECONDS);

      List<Object> outcomes = List.of(outcomeOne, outcomeTwo);

      assertThat(workersAreReady).isTrue();
      assertThat(outcomes).contains("secret");
      assertThat(outcomes.stream().filter(IllegalStateException.class::isInstance).count())
          .isEqualTo(1);
    }
  }
}
