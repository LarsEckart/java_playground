package de.larseckart.spielplatz.javaspecialists.issue29;

public class ByteFactory implements ObjectFactory {

    public Object makeObject() {
        return new Byte((byte) 33);
    }
}
