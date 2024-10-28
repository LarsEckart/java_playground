package lars.spielplatz.tc;

import java.time.LocalDateTime;

class ChatHistoryLine {

  public String who;
  public String what;
  public LocalDateTime when;

  public ChatHistoryLine(String who, String what, LocalDateTime when) {
    this.who = who;
    this.what = what;
    this.when = when;
  }

  String renderChatHistoryLine() {
    return String.format("[%s] %s: %s", this.when, this.who, this.what);
  }
}
