package ee.lars.di.dagger2.computer;

import javax.inject.Inject;

public class Screen {

    @Inject
    public Screen() {
    }

    public void print(String s) {
        System.out.println("===" + s + "===");
    }
}
