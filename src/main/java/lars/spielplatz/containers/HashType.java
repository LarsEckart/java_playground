package lars.spielplatz.containers;

public class HashType extends SetType {

  public HashType(int i) {
    super(i);
  }

  @Override
  public int hashCode() {
    return i;
  }
}
