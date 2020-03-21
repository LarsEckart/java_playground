package lars.refactoring.primitiveobsession;

/**
 * 1) Starting Small, change from int to ThingyId inside of Thingy only and make it compile again.
 * 2) Pushing Arguments Out, extract parameter in setter 3) Pushing Return Values, Extract Method on
 * field in getter and then Inline original getter and rename extracted method. 4) Pushing Arguments
 * In
 */
public class Thingy {

  private int thingyId;

  public int getThingyId() {
    return thingyId;
  }

  public void setThingyId(int thingyId) {
    this.thingyId = thingyId;
  }
}

class PseudoThingyClient {

  public void assignThingy() {
    Thingy thingy = new Thingy();
    thingy.setThingyId(3);
  }

  public void useThingy() {
    Thingy thingy = new Thingy();
    int thingyId = thingy.getThingyId();
    doThingyStuff(thingyId);
  }

  private void doThingyStuff(int thingyId) {
    Gizmo g = new Gizmo();
    g.doTheThing(
        thingyId); // here also rewrite to new ThingyId(thingyId).intValue() and extract parameter
                   // then
  }
}

class Gizmo {

  public void doTheThing(int thingyId) {}
}
