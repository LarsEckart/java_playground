package lars.spielplatz;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ModelReader {

  public Map<String, TreeSet<String>> getEntitiesByPackage(
      Set<String> packageFilterSet, Set<String> entityFilterSet) {
    return this.getEntityNames().stream()
        .map(
            _entityName ->
                new Object() { // anonymous inner class
                  String entityName = _entityName; // need to redeclare it
                  ModelEntity entity = getModelEntity(_entityName);
                  String packageName = entity.getPackageName();
                })
        .filter(tuple -> matchesPackageFilter(packageFilterSet, tuple.packageName))
        .filter(tuple -> matchesEntityFilter(entityFilterSet, tuple.entityName))
        .collect(
            Collectors.groupingBy(
                tuple -> tuple.packageName,
                Collectors.mapping(
                    tuple -> tuple.entityName, Collectors.toCollection(TreeSet::new))));
  }

  private boolean matchesPackageFilter(Set<String> packageFilterSet, String packageName) {
    return UtilValidate.isEmpty(packageFilterSet)
        || packageFilterSet.stream().anyMatch(packageName::contains);
  }

  private boolean matchesEntityFilter(Set<String> entityFilterSet, String entityName) {
    return UtilValidate.isEmpty(entityFilterSet) || entityFilterSet.contains(entityName);
  }

  private ModelEntity getModelEntity(String entityName) {
    throw new UnsupportedOperationException("implement me!");
  }

  private List<String> getEntityNames() {
    throw new UnsupportedOperationException("implement me!");
  }

  private class ModelEntity {

    public String getPackageName() {
      throw new UnsupportedOperationException("implement me!");
    }
  }

  private static class UtilValidate {

    public static boolean isEmpty(Set<String> packageFilterSet) {
      throw new UnsupportedOperationException("implement me!");
    }
  }
}
