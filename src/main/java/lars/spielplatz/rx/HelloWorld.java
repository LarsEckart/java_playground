package lars.spielplatz.rx;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;

public class HelloWorld {

  public static void main(String[] args) {
    Disposable helloWorld = Flowable.just("Hello world").subscribe(System.out::println);
  }
}
