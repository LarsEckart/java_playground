package ee.lars.di.dagger2.computer;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.inject.Inject;

public class Keyboard {

    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());

    @Inject
    public Keyboard() {

    }

    public String use() {
        return this.scanner.nextLine();
    }
}
