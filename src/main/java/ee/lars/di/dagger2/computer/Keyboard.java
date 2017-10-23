package ee.lars.di.dagger2.computer;

import java.util.Scanner;
import javax.inject.Inject;

public class Keyboard {

    private final Scanner scanner = new Scanner(System.in);

    @Inject
    public Keyboard() {

    }

    public String use() {
        return this.scanner.nextLine();
    }
}
