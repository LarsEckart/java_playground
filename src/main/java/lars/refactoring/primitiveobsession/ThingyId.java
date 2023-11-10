package lars.refactoring.primitiveobsession;

public final class ThingyId {

  private final int id;

  public ThingyId(int id) {
    this.id = id;
  }

  public int toInt() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    return (o instanceof ThingyId t) && id == t.id;
  }

  @Override
  public int hashCode() {
    return id;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(" + id + ")";
  }
}
