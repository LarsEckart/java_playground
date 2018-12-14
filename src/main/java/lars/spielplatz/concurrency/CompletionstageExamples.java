package lars.spielplatz.concurrency;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletionstageExamples {

    /*
    if creating the chain is already costly, do it asynchronly too!
     */
    public void move_creation_to_other_thread() {
        ExecutorService executor = Executors.newCachedThreadPool();

        CompletableFuture<Void> start = new CompletableFuture<>();
        CompletableFuture<List<Long>> readIds = start.thenCompose(nil -> getUserIds());
        readIds.thenRun(() -> System.out.println("ids read"));
        readIds.thenCompose(this::getUsersFromDb)
                .thenCompose(this::sendEmails);

        start.completeAsync(() -> null, executor);
        executor.shutdown();
    }

    private CompletionStage<Object> sendEmails(List<String> users) {
        throw new UnsupportedOperationException("implement me!");
    }

    private CompletionStage<List<String>> getUsersFromDb(List<Long> ids) {
        throw new UnsupportedOperationException("implement me!");
    }

    private CompletionStage<List<Long>> getUserIds() {
        throw new UnsupportedOperationException("implement me!");
    }
}
