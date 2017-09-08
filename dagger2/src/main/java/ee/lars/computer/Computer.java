package ee.lars.computer;

import javax.inject.Inject;

public class Computer {

    // private fields cannot be field-injected!
    @Inject
    Keyboard keyboard;

    @Inject
    Screen screen;

    @Inject
    Printer printer;

    // the inject annotation here tells dagger to use this constructor to create
    // instances.
    @Inject
    public Computer() {
    }

    public void use() {
        String input = this.keyboard.use();
        this.screen.print(input);
        this.printer.print(input);
    }
}
