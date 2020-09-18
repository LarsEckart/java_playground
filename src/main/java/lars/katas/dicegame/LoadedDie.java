package lars.katas.dicegame;

class LoadedDie extends Die {

  private final int value;

  public LoadedDie(int value) {
    this.value = value;
  }

  @Override public int faceValue() {
    return value;
  }
}
