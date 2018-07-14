package lars.spielplatz.javaspecialists.issue224;

import java.util.Arrays;

public class IdentityAnonymousLambda {

    public static void main(String... args) {
        for (int i = 0; i < 2; i++) {
            showIdentity(() ->
                    System.out.println("Lambda - no fields"));

            showIdentity(() ->
                    System.out.println("Lambda - parameters - " + Arrays.toString(args)));

            showIdentity(() -> System.out.println("anon - no fields"));

            showIdentity(() -> System.out.println("anon - parameters - " + Arrays.toString(args)));
            System.out.println();
        }
    }

    private static void showIdentity(Runnable runnable) {
        System.out.printf("%x ", System.identityHashCode(runnable));
        runnable.run();
    }
}
