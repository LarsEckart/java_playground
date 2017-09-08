package ee.lars.computer;

public class Main {

    public static void main(String[] args) {
        ComputerInjector injector = DaggerComputerInjector.create();
        injector.computer().use();
    }
}
