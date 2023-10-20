package lars.spielplatz.concurrency;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import org.junit.jupiter.api.Test;

class CompletableFuturesTest {

  @Test
  void withJoin() {
    CompletableFuture<Void> firstFuture = CompletableFuture.runAsync(() -> {});

    CompletableFuture<Void> secondFuture =
        CompletableFuture.runAsync(
            () -> {
              throw new RuntimeException("boom");
            });

    List<CompletableFuture<Void>> futures = List.of(firstFuture, secondFuture);

    assertThrows(RuntimeException.class, () -> futures.forEach(f -> f.join()));
  }

  @Test
  void noJoinNoThrow() {
    CompletableFuture<Void> firstFuture = CompletableFuture.runAsync(() -> {});

    CompletableFuture<Void> secondFuture =
        CompletableFuture.runAsync(
            () -> {
              throw new RuntimeException("boom");
            });

    List<CompletableFuture<Void>> futures = List.of(firstFuture, secondFuture);
  }

  @Test
  void allOfJoinThrows() {
    CompletableFuture<Void> firstFuture = CompletableFuture.runAsync(() -> {});

    CompletableFuture<Void> secondFuture =
        CompletableFuture.runAsync(
            () -> {
              throw new RuntimeException("boom");
            });

    CompletableFuture<Void> allFutures = CompletableFuture.allOf(firstFuture, secondFuture);

    assertThrows(RuntimeException.class, allFutures::join);
  }

  @Test
  void allOfJoinDoOthersCompleteThrows() {
    CompletableFuture<Void> firstFuture =
        CompletableFuture.runAsync(
            () -> {
              System.out.println("1");
            });
    CompletableFuture<Void> secondFuture =
        CompletableFuture.runAsync(
            () -> {
              System.out.println("2");
              throw new IllegalArgumentException("boom");
            });
    CompletableFuture<Void> thirdFuture =
        CompletableFuture.runAsync(
            () -> {
              System.out.println("3");
            });
    CompletableFuture<Void> fourthFuture =
        CompletableFuture.runAsync(
            () -> {
              try {
                Thread.sleep(500);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }

              System.out.println("4");
            });
    CompletableFuture<Void> secondFuture2 =
        CompletableFuture.runAsync(
            () -> {
              System.out.println("22");
              throw new IllegalStateException("boom");
            });

    CompletableFuture<Void> allFutures =
        CompletableFuture.allOf(
            firstFuture, secondFuture, thirdFuture, fourthFuture, secondFuture2);

    System.out.println("before");
    CompletionException completionException =
        assertThrows(CompletionException.class, allFutures::join);

    System.out.println(completionException.getCause());
  }
}
