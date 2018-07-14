package lars.spielplatz.rx;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

public class HelloWorld {

    public static void main(String[] args) {
        Disposable helloWorld = Flowable.just("Hello world").subscribe(System.out::println);
    }
}
