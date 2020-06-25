package lars.katas.bottomofallthings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class MostlyRandom implements Order {

  @Override
  public List<String> order(List<String> input) {
    List<String> result = new ArrayList<>(input.subList(0, input.size() - 1));
    Collections.shuffle(result);
    result.add(input.get(input.size() - 1));
    return result;
  }
}
