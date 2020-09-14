package lars.katas;

public class StringCalculator {

  private final Splitter splitter;
  private final Transformer transformer;

  public StringCalculator(Splitter splitter, Transformer transformer) {
    this.splitter = splitter;
    this.transformer = transformer;
  }

  public int add(String numbers) {
    String[] split = splitter.split(numbers);

    return transformer.transform(split[0]);
  }
}
