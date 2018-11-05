package lars.spielplatz.rx;

import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

public class JavaExamples {

    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(1, 1, TimeUnit.SECONDS)
                .map(i -> i * 2)
                .subscribe(System.out::println);

        sleep(10000);
    }

    @Deprecated(since = "4.2", forRemoval = true)

    private static boolean sleep(int ms) {
        try {
            Thread.sleep(ms);
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }
}
