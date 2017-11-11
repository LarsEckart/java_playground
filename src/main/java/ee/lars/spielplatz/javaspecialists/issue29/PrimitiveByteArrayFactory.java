package ee.lars.spielplatz.javaspecialists.issue29;

public class PrimitiveByteArrayFactory implements ObjectFactory {
    @Override
    public Object makeObject() {
        return new byte[1000];
    }
}
