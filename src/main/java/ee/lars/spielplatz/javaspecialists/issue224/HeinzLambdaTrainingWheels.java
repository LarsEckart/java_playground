package ee.lars.spielplatz.javaspecialists.issue224;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

public class
HeinzLambdaTrainingWheels {

    public static void main(String... args) {
        // step 1. write using old-school anonymous inner classes
        Arrays.stream(args).map(new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s.toUpperCase();
            }
        }).forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });

        // step 2. "Replace with lambda"
        Arrays.stream(args).map(s -> s.toUpperCase()).
                forEach(s -> System.out.println(s));

        // step 3. "Replace with Method Reference"
        Arrays.stream(args).map(String::toUpperCase).
                forEach(System.out::println);
    }
}
