package lars.spielplatz.containers;

public class SetType {

  int i;

  public SetType(int i) {
    this.i = i;
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof SetType && (i == ((SetType) o).i);
  }

  @Override
  public String toString() {
    return Integer.toString(i);
  }
}
