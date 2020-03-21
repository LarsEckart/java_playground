package lars.spielplatz.javaspecialists.issue29;

public class ThreeByteFactory implements ObjectFactory {

  @Override
  public Object makeObject() {
    return new ThreeBytes();
  }

  private static class ThreeBytes {

    byte b0, b1, b2;
  }
}
