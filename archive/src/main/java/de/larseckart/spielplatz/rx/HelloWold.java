package de.larseckart.spielplatz.rx;

import rx.Observable;
import rx.functions.Action1;

public class HelloWold {

    public static void main(String... names) {
        Observable.from(names).subscribe(s -> {
            System.out.println("Hello " + s + "!");
        });
    }
}
