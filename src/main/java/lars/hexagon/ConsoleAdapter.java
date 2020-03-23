package lars.hexagon;

// left side adapter
public class ConsoleAdapter {

  private final IRequestVerses poetryReader;
  private final IWriteLines publicationStrategy;

  public ConsoleAdapter(IRequestVerses poetryReader, IWriteLines publicationStrategy) {
    this.poetryReader = poetryReader;
    this.publicationStrategy = publicationStrategy;
  }

  public ConsoleAdapter(PoetryReader poetryReader) {
    this(poetryReader, new SysOutPublicationStrategy());
  }

  public void ask() {
    String verses = poetryReader.giveMeSomePoetry();
    publicationStrategy.writeLines(verses);
  }

  static class SysOutPublicationStrategy implements IWriteLines {

    @Override
    public void writeLines(String text) {
      System.out.println(text);
    }
  }
}
