package lars.spielplatz.containers;

import java.lang.reflect.Field;
import java.util.HashMap;

class ContainerSize {


  private enum States {
    dummy_val_01,
    dummy_val_02,
    dummy_val_03,
    dummy_val_04,
    dummy_val_05,
    dummy_val_06,
    dummy_val_07,
    dummy_val_08,
    dummy_val_09,
    dummy_val_10,
    dummy_val_11,
    dummy_val_12,
    dummy_val_13,
    dummy_val_14,
    dummy_val_15,
    dummy_val_16,
    dummy_val_17,
    dummy_val_18,
    dummy_val_19,
    dummy_val_20,
  }

  /*
   * add "--add-opens java.base/java.util=ALL-UNNAMED" to vm options in intellij otherwise this
   * won't work.
   */
  public static void main(String[] args) throws Exception {
    var oldWay = new HashMap<>(States.values().length);
    var newWay = HashMap.newHashMap(States.values().length);

    System.out.println("threshold after creation");
    System.out.println(getThreshold(newWay));
    System.out.println(getThreshold(oldWay));

    for (States s : States.values()) {
      oldWay.put(s.name(), s);
      newWay.put(s.name(), s);
    }

    System.out.println("threshold after filling");
    System.out.println(getThreshold(newWay));
    System.out.println(getThreshold(oldWay));
  }

  private static int getThreshold(HashMap<Object, Object> map)
      throws NoSuchFieldException, IllegalAccessException {
    Field f = map.getClass().getDeclaredField("threshold"); //NoSuchFieldException
    f.setAccessible(true);
    return (int) f.get(map); //IllegalAccessException
  }

}
