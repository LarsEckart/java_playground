package lars.katas;

class CommaNewLineSplitter implements Splitter {

  private final Splitter splitter;

  public CommaNewLineSplitter(Splitter splitter) {
    this.splitter = splitter;
  }

  @Override
  public String[] split(String numbers) {
    String result = numbers.replace("\n", ",");
    return splitter.split(result);
  }
}
