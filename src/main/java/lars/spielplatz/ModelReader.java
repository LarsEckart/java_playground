package lars.spielplatz;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ModelReader {

    public Map<String, TreeSet<String>> getEntitiesByPackage(
            Set<String> packageFilterSet, Set<String> entityFilterSet) {

        Map<String, TreeSet<String>> entitiesByPackage = new HashMap<>();
        //put the entityNames TreeSets in a HashMap by packageName
        Iterator<String> ecIter = this.getEntityNames().iterator();
        while (ecIter.hasNext()) {
            String entityName = ecIter.next();
            ModelEntity entity = this.getModelEntity(entityName);
            String packageName = entity.getPackageName();
            if (!packageFilterSet.isEmpty()) {
                // does it match any of these?
                boolean foundMatch = false;
                for (String packageFilter : packageFilterSet) {
                    if (packageName.contains(packageFilter)) {
                        foundMatch = true;
                    }
                }
                if (!foundMatch) {
                    continue;
                }
            }
            if (!entityFilterSet.isEmpty()
                    && !entityFilterSet.contains(entityName)) {
                continue;
            }
            TreeSet<String> entities = entitiesByPackage.get(packageName);
            if (entities == null) {
                entities = new TreeSet<>();
                entitiesByPackage.put(packageName, entities);
            }
            entities.add(entityName);
        }
        return entitiesByPackage;
    }


    private ModelEntity getModelEntity(String entityName) {
        throw new UnsupportedOperationException("implement me!");
    }

    private Iterable<String> getEntityNames() {
        throw new UnsupportedOperationException("implement me!");
    }

    private class ModelEntity {

        public String getPackageName() {
            throw new UnsupportedOperationException("implement me!");
        }
    }
}
