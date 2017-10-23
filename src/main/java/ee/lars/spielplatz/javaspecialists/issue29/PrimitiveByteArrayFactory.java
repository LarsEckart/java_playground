package ee.lars.spielplatz.javaspecialists.issue29;

public class PrimitiveByteArrayFactory implements ObjectFactory {
    public Object makeObject() {
        return new byte[1000];
    }
}
