package lars.katas.snooker;

class PlayerActor {

  String playerName;
  SnookerScorer snookerGame;

  public PlayerActor(String playerName, SnookerScorer snookerGame) {
    this.playerName = playerName;
    this.snookerGame = snookerGame;
  }

  void pots(String colour) {
    this.snookerGame.record(
        ShotBuilder.forPlayer(this.playerName).hits(colour).pots(colour).shot());
  }

  void missesRed() {
    this.snookerGame.record(ShotBuilder.forPlayer(this.playerName).hits("red").shot());
  }
}
