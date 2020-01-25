package lars.spielplatz.javaspecialists.issue235;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapClashInspector {
  private interface MapProcessor {
    void beginBucket();

    void process(Map.Entry<?, ?> node);

    void endBucket(Map<Integer, Integer> count);
  }

  /**
   * Returns a map showing as key the number of clashes and as value the number of entries with
   * identical hash codes. With a "perfect" hash function, the map will contain only one entry with
   * 1 as a key and the number of entries in the map as a value.
   */
  public static Map<Integer, Integer> getHashClashDistribution(Map<?, ?> map)
      throws NoSuchFieldException, IllegalAccessException {
    return getBucketDistribution(
        map,
        new MapProcessor() {
          private final Map<Integer, Integer> numberOfClashes = new HashMap<Integer, Integer>();

          public void beginBucket() {
            numberOfClashes.clear();
          }

          public void process(Map.Entry<?, ?> node) {
            increment(numberOfClashes, node.getKey().hashCode());
          }

          public void endBucket(Map<Integer, Integer> count) {
            for (Integer val : numberOfClashes.values()) {
              increment(count, val);
            }
          }
        });
  }

  /**
   * Returns a map showing as key the number of clashes and as value the number of buckets with this
   * number of clashes. In a "perfect" distribution, we would have 1->numberOfEntriesInMap. The
   * worst possible distribution is numberOfEntriesInMap->1, where all the entries go into a single
   * bucket. It also shows the number of empty buckets. The Java 8 HashMap copes well with clashes,
   * but earlier versions would become very slow due to O(n) lookup.
   */
  public static Map<Integer, Integer> getBucketClashDistribution(Map<?, ?> map)
      throws NoSuchFieldException, IllegalAccessException {
    return getBucketDistribution(
        map,
        new MapProcessor() {
          private int size;

          public void beginBucket() {
            size = 0;
          }

          public void process(Map.Entry<?, ?> node) {
            size++;
          }

          public void endBucket(Map<Integer, Integer> count) {
            increment(count, size);
          }
        });
  }

  /** Increment the value if already exists; otherwise set to 1. */
  private static void increment(Map<Integer, Integer> map, int size) {
    Integer counter = map.get(size);
    if (counter == null) {
      map.put(size, 1);
    } else {
      map.put(size, counter + 1);
    }
  }

  private static Map<Integer, Integer> getBucketDistribution(Map<?, ?> map, MapProcessor processor)
      // Since Java 7, we can throw ReflectiveOperationException
      throws NoSuchFieldException, IllegalAccessException {
    Map.Entry<?, ?>[] table = getTable(map);
    Field nextNodeField = getNextField(table);
    Map<Integer, Integer> numberPerBucket = new TreeMap<Integer, Integer>();
    for (Map.Entry<?, ?> node : table) {
      processor.beginBucket();
      while (node != null) {
        processor.process(node);
        node = (Map.Entry<?, ?>) nextNodeField.get(node);
      }
      processor.endBucket(numberPerBucket);
    }
    return numberPerBucket;
  }

  private static Map.Entry<?, ?>[] getTable(Map<?, ?> map)
      throws NoSuchFieldException, IllegalAccessException {
    Field tableField = map.getClass().getDeclaredField("table");
    tableField.setAccessible(true);
    return (Map.Entry<?, ?>[]) tableField.get(map);
  }

  private static Field getNextField(Object table) throws NoSuchFieldException {
    Class<?> nodeType = table.getClass().getComponentType();
    Field nextNodeField = nodeType.getDeclaredField("next");
    nextNodeField.setAccessible(true);
    return nextNodeField;
  }
}
