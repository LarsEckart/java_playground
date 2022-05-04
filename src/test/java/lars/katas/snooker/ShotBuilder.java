package lars.katas.snooker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShotBuilder {

  String playerName;
  String hit = "";
  List<String> potted = new ArrayList<>();

  public ShotBuilder(String playerName) {
    this.playerName = playerName;
  }

  /**
   * returns a ShotBuilder (call .shot() to return the shot object) by default the shot will assume
   * the player hit and potted no balls
   */
  static ShotBuilder forPlayer(String playerName) {
    return new ShotBuilder(playerName);
  }

  /**
   * Shortcut for a legal red-ball pot
   *
   * @returns {{hitFirst: string, pottedBalls: *[], player: string}}
   */
  static Shot potRed(String playerName) {
    return potColour(playerName, "red");
  }

  /**
   * Shortcut for a legal colour-ball pot
   *
   * @param playerName
   * @returns {{hitFirst: string, pottedBalls: *[], player: string}}
   */
  static Shot potColour(String playerName, String colour) {
    return new ShotBuilder(playerName)
        .hits(colour)
        .pots(colour)
        .shot();
  }

  /**
   * hits specifies the first ball the player hit with the cueball
   *
   * @returns {ShotBuilder}
   */
  ShotBuilder hits(String ball) {
    this.hit = ball;
    return this;
  }

  /**
   * pots is an ordered array of balls that the player potted
   * <p>
   * Calling this method multiple times will add to the balls potted by the player for this shot.
   * You may pass in an array of balls or list of balls as arguments
   *
   * @returns {ShotBuilder}
   */
  ShotBuilder pots(String... balls) {
    Collections.addAll(this.potted, balls);
    return this;
  }

  /**
   * Build the shot object used by the production code
   *
   * @returns {{hitFirst: string, pottedBalls: *[], player: string}}
   */
  public Shot shot() {
    return new Shot(this.playerName, this.potted, this.hit);
  }
}


