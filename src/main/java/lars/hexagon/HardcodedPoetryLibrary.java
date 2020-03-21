package lars.hexagon;

class HardcodedPoetryLibrary implements IObtainPoetry {

  @Override
  public String getMeAPoem() {
    return "Sie sagt, ich sei so oldschool, oldschool\n"
        + "Ich weiß nicht mal, was das heißt\n"
        + "Die Bärte werden länger, die Hosen enger\n"
        + "Doch ich trag' immer noch 501";
  }
}
