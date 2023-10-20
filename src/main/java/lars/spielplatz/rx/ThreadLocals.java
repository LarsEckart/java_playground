package lars.spielplatz.rx;

import io.reactivex.rxjava3.core.Flowable;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * code from https://bsideup.github.io/posts/daily_reactive/thread_locals/ explaining the issue with
 * threadlocals in reactive code.
 */
public class ThreadLocals {

  static final ThreadLocal<String> USER_ID = new ThreadLocal<>();

  public static void main(String[] args) {
    naiveApproach();

    Mono.just("Hello %s")
        .delaySubscription(Flowable.timer(1, TimeUnit.SECONDS))
        .transform(
            flux ->
                Mono.deferWithContext(
                    ctx -> {
                      return flux.doOnNext(
                          greeting -> {
                            // Get it from the Context
                            String userId = ctx.get("userId");
                            System.out.println(String.format(greeting, userId));
                          });
                    }))
        // Put something to the Context, e.g. in the web filter
        .subscriberContext(Context.of("userId", "bsideup"))
        .block();
  }

  private static void naiveApproach() {
    USER_ID.set("bsideup");

    Mono.just("Hello %s")
        .delayElement(Duration.ofSeconds(1))
        .doOnNext(
            greeting -> {
              // WIll print "Hello null". Bummer!
              System.out.println(String.format(greeting, USER_ID.get()));
            })
        .block();
  }
}
