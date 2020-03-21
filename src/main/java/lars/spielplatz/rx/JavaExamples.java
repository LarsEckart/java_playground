package lars.spielplatz.rx;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;

public class JavaExamples {

  public static void main(String[] args) throws InterruptedException {
    Disposable disposable =
        Flowable.interval(1, 1, TimeUnit.SECONDS).map(i -> i * 2).subscribe(System.out::println);

    sleep(10000);
  }

  private static boolean sleep(int ms) {
    try {
      Thread.sleep(ms);
      return true;
    } catch (InterruptedException e) {
      return false;
    }
  }
}
