package ee.lars.computer;

/**
 * 3rd party class where we cannot add inject annotation so dagger
 * does not know how to create an instance, therefore we need a
 * Provider.
 */
public class PrinterImpl implements Printer {

    @Override
    public void print(String s) {
        System.out.println("~~~" + s + "~~~");
    }
}
