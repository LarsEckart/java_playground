package lars.spielplatz.javaspecialists.issue29;

public class StringFactory implements ObjectFactory {

    @Override
    public Object makeObject() {
        StringBuilder buf = new StringBuilder(12);
        buf.append("Hello ");
        buf.append("World!");
        return buf.toString();
    }
}
