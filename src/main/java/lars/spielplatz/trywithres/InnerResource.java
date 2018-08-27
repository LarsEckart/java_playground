package lars.spielplatz.trywithres;

public class InnerResource implements AutoCloseable {

    public InnerResource() {
        System.out.println("InnerResource created.");
    }

    public InnerResource(
            final RuntimeException exceptionToThrow) {
        throw exceptionToThrow != null
                ? exceptionToThrow
                : new RuntimeException("InnerResource: No exception provided.");
    }

    @Override
    public void close() throws Exception {
        System.out.println("InnerResource closed.");
    }

    @Override
    public String toString() {
        return "InnerResource";
    }
}
