package lars.katas.snooker;

record Turn(String nextPlayer, boolean isFoul, int breakScore, String winner) {

  public Turn(String nextPlayer, boolean isFoul, int breakScore) {
    this(nextPlayer, isFoul, breakScore, "");
  }
}
