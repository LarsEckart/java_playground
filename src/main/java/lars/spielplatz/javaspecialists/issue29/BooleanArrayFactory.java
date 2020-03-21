package lars.spielplatz.javaspecialists.issue29;

public class BooleanArrayFactory implements ObjectFactory {

  @Override
  public Object makeObject() {
    Boolean[] objs = new Boolean[1000];
    for (int i = 0; i < objs.length; i++) objs[i] = Boolean.TRUE;
    return objs;
  }
}
