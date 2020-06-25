package lars.katas.bottomofallthings;

import java.util.ArrayList;
import java.util.List;

class Default implements Order {

  @Override
  public List<String> order(List<String> input) {
    return new ArrayList<>(input);
  }
}
