package lars.spielplatz.trywithres;

public class OuterResource implements AutoCloseable {

    private final InnerResource wrappedInnerResource;

    public OuterResource(final InnerResource newInnerResource) {
        System.out.println("OuterResource created.");
        wrappedInnerResource = newInnerResource;
    }

    public OuterResource(
            final InnerResource newInnerResource,
            final RuntimeException exceptionToThrow) {
        wrappedInnerResource = newInnerResource;
        throw exceptionToThrow != null
                ? exceptionToThrow
                : new RuntimeException("OuterResource: No exception provided.");
    }

    @Override
    public void close() throws Exception {
        System.out.println("OuterResource closed.");
    }

    @Override
    public String toString() {
        return "OuterResource";
    }
}
