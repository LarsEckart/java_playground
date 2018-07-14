package lars.spielplatz.javaspecialists.issue29;

public class ByteFactory implements ObjectFactory {

    @Override
    public Object makeObject() {
        return (byte) 33;
    }
}
