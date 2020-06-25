package lars.katas.bottomofallthings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Random implements Order{

  @Override
  public List<String> order(List<String> input) {
    List<String> result = new ArrayList<>(input);
    Collections.shuffle(result);
    return result;
  }
}
