package lars.katas;

class CommaSplitter implements Splitter {

  @Override
  public String[] split(String numbers) {
    return numbers.split(",");
  }

}
