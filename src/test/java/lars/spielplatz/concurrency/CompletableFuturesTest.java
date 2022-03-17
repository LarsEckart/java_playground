package lars.spielplatz.concurrency;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;

class CompletableFuturesTest {

  @Test
  void withJoin() {
    CompletableFuture<Void> firstFuture = CompletableFuture.runAsync(() -> {

    });
    CompletableFuture<Void> secondFuture = CompletableFuture.runAsync(() -> {
      throw new RuntimeException("boom");
    });

    List<CompletableFuture<Void>> futures = List.of(firstFuture, secondFuture);

    assertThrows(RuntimeException.class, () -> futures.forEach(f -> f.join()));
  }

  @Test
  void noJoinNoThrow() {
    CompletableFuture<Void> firstFuture = CompletableFuture.runAsync(() -> {

    });
    CompletableFuture<Void> secondFuture = CompletableFuture.runAsync(() -> {
      throw new RuntimeException("boom");
    });

    List<CompletableFuture<Void>> futures = List.of(firstFuture, secondFuture);
  }

  @Test
  void allOfJoinThrows() {
    CompletableFuture<Void> firstFuture = CompletableFuture.runAsync(() -> {

    });
    CompletableFuture<Void> secondFuture = CompletableFuture.runAsync(() -> {
      throw new RuntimeException("boom");
    });

    CompletableFuture<Void> allFutures = CompletableFuture.allOf(firstFuture, secondFuture);

    assertThrows(RuntimeException.class, allFutures::join);
  }
}
